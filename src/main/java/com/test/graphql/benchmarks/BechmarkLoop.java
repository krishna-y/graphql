package com.test.graphql.benchmarks;

import com.test.graphql.dao.entity.Organization;
import com.test.graphql.dao.entity.Person;
import com.test.graphql.dao.entity.Vehicle;
import com.test.graphql.dao.repository.OrganizationRepository;
import com.test.graphql.dao.repository.PersonRepository;
import com.test.graphql.dao.repository.VehicleRepository;
import com.test.graphql.query.OrganizationQuery;
import com.test.graphql.query.VehicleQuery;
import com.test.graphql.resolvers.OrganizationResolver;
import com.test.graphql.service.VehicleService;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.execution.instrumentation.dataloader.DataLoaderDispatcherInstrumentationOptions;
import graphql.schema.AsyncDataFetcher;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

/*
http://hg.openjdk.java.net/code-tools/jmh/file/tip/jmh-samples/src/main/java/org/openjdk/jmh/samples/
*/
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
@Fork(value = 2, jvmArgs = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 3)
@Measurement(iterations = 3)
public class BechmarkLoop {
    VehicleRepository vehicleRepository;

    private GraphQL graphQL;
    private VehicleQuery vehicleQuery;
    private OrganizationQuery organizationQuery;
    private OrganizationResolver organizationResolver;

    private List<String> DATA_FOR_TESTING;

    public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()
                .include(BechmarkLoop.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup() throws IOException {

        this.vehicleRepository = new VehicleRepository();
        vehicleRepository.addVehicle(new Vehicle(1, "hatchback", "nano", "tata",
                LocalDate.now(), "2020"));
        VehicleService vehicleService = new VehicleService(vehicleRepository);
        vehicleQuery = new VehicleQuery(vehicleService);
        this.organizationQuery = new OrganizationQuery(new OrganizationRepository());
        this.organizationResolver = new OrganizationResolver(vehicleRepository);
        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(getSchema());
        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Entity")
                        .typeResolver(env -> {
                            Object javaObject = env.getObject();
                            if (javaObject instanceof Person) {
                                return env.getSchema().getObjectType("Person");
                            } else if (javaObject instanceof Organization) {
                                return env.getSchema().getObjectType("Organization");
                            } else {
                                throw new RuntimeException("Invalid Type");
                            }
                        }))
                .type(newTypeWiring("Query")
                        .dataFetcher("vehicles", vehicleQuery.getVehicles()))
                .type(newTypeWiring("Query")
                        .dataFetcher("organizations", organizationQuery.getOrganizations()))
                .type(newTypeWiring("Organization")
                        .dataFetcher("vehicles", AsyncDataFetcher.async(organizationResolver.vehicles())))
                .build();

        DataLoaderDispatcherInstrumentationOptions options = DataLoaderDispatcherInstrumentationOptions
                .newOptions().includeStatistics(true);
        SchemaGenerator generator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = generator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
        GraphQL graphQL = GraphQL.newGraphQL(graphQLSchema)
                .build();

        this.graphQL = graphQL;

    }

    @Benchmark
    public void runVehicleQuery(Blackhole bh) {

        ExecutionResult execute = graphQL.execute("query{\n" +
                " vehicles{\n" +
                "    id,\n" +
                "    }\n" +
                "}");
        bh.consume(execute);
    }

    @Benchmark
    public void runOrganizationQuery(Blackhole bh) {

        ExecutionResult execute =  graphQL.execute("query{\n" +
                " organizations{\n" +
                "    id,\n" +
                "    name,\n" +
                "    vehicles,\n" +
                "    }\n" +
                "}");
        Object data = execute.getData();
        bh.consume(execute);
    }

    @Benchmark
    public void runOnlyOrganizationResolver(Blackhole bh) throws Exception {
        DataFetcher organizations = organizationQuery.getOrganizations();
        Object o = organizations.get(null);
//        System.out.println(o);
        bh.consume(o);
    }

    @Benchmark
    public void runOnlyVehicleResolver(Blackhole bh) throws Exception {
        DataFetcher vehicles = vehicleQuery.getVehicles();
        Object obj = vehicles.get(null);
//        System.out.println(obj);
        bh.consume(obj);
    }

    public String getSchema(){
        return "type Vehicle{\n" +
                "        id: ID!,\n" +
                "        type: String,\n" +
                "        modelCode: String,\n" +
                "        brandName: String,\n" +
                "        launchDate: String\n" +
                "}\n" +
                "\n" +
                "\n" +
                "type Query {\n" +
                "        vehicles:[Vehicle]\n" +
                "        organizations:[Organization]\n" +
                "}\n" +
                "\n" +
                "\n" +
                "type Organization{\n" +
                "        id : String,\n" +
                "        name : String,\n" +
                "        vehicles : [ID]\n" +
                "}";
    }
}
