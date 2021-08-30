package com.test.graphql.query;

import com.test.graphql.dao.entity.Vehicle;
import com.test.graphql.service.VehicleService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetcher;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class VehicleQuery implements GraphQLQueryResolver {

    private VehicleService vehicleService;

    @Autowired
    public VehicleQuery(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    public DataFetcher getVehicles() {
        return dataFetchingEnvironment -> this.vehicleService.getAllVehicles(10);
    }

    public Optional<Vehicle> getVehicle(final int id) {
        return this.vehicleService.getVehicle(id);
    }
}
