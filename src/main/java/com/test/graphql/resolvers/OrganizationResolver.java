package com.test.graphql.resolvers;

import com.test.graphql.dao.entity.Organization;
import com.test.graphql.dao.entity.Vehicle;
import com.test.graphql.dao.repository.VehicleRepository;
import graphql.kickstart.tools.GraphQLResolver;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class OrganizationResolver implements GraphQLResolver<Organization> {
    private final VehicleRepository vehicleRepository;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Autowired
    public OrganizationResolver(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public DataFetcher vehicles() {

        return dataFetchingEnvironment->{
//            System.out.println(Thread.currentThread().getName() + " Organization Vehicle Thread Start");
            Organization organization = dataFetchingEnvironment.getSource();
            List<Integer> vehicleIds = organization.getVehicleIds();
            List<Vehicle> v = new ArrayList<>();
            for (int i = 0; i < vehicleIds.size(); i++) {
                v.add(vehicleRepository.getVehicle(vehicleIds.get(i)));
            }
//            System.out.println(Thread.currentThread().getName() + " Organiation Vehicle Thread End");
            return v;
        };
    }

    public String name(Organization organization){
        return "Custom Scalar Prefix : "  + organization.getName();
    }
}
