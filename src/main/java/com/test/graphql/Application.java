package com.test.graphql;

import com.test.graphql.dao.entity.Organization;
import com.test.graphql.dao.entity.Person;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;

/*
1. Things can be made parallel for different resolvers
2. If a field is omitted in graphql query its not fetched. The resolver for it is not called
3. How to select an item in an array.


mutation{
 addPerson(name : "krishna", vehicles : [1,2], housese : [1,2]) {
    id,
    name,
    vehicles{
      id,
      type,
      modelCode,
      brandName,
      launchDate
    },
    houses{
      id,
      city,
      address
    }
  }
}


query{
 person(id :1){
    id,
    name,
    vehicles{
      id,
      type,
      modelCode,
      brandName,
      launchDate
    }
  }
}



4. Application Returning combination of any different type of objects - Done
5. It should return any combinations of above items. - Done
6. Same resolver for two different fields ? Not clear

query{
  getEntities(count: 6){
  	id,
    __typename ,

        __typename
    ... on Person {
      houses{
        id,
        address

      }
    }
    ... on Organization {
      vehicles{
        id,
        modelCode,
        brandName
      }
    }
  }
}


 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {
    @Value("classpath:graphql/vehicleql.graphqls")
    Resource resource;

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

    @Bean
    public GraphQL graphQL() throws IOException {
        File schemaFile = resource.getFile();
        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schemaFile);
        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .type(TypeRuntimeWiring.newTypeWiring("Entity")
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
                .build();


        SchemaGenerator generator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = generator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
        return GraphQL.newGraphQL(graphQLSchema).build();
    }
}
