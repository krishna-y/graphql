package com.test.graphql.resolvers;

import com.test.graphql.dao.entity.Organization;
import com.test.graphql.dao.entity.Vehicle;
import com.test.graphql.dao.repository.VehicleRepository;
import graphql.kickstart.tools.GraphQLResolver;
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

    public CompletableFuture<List<Vehicle>> vehicles(Organization organization) {
        return CompletableFuture.supplyAsync(() -> {
//            System.out.println(Thread.currentThread().getName() + " Organization Vehicle Thread Start");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<Integer> vehicleIds = organization.getVehicleIds();
            List<Vehicle> v = new ArrayList<>();
            for (int i = 0; i < vehicleIds.size(); i++) {
                v.add(vehicleRepository.getVehicle(vehicleIds.get(i)));
            }
//            System.out.println(Thread.currentThread().getName() + " Organiation Vehicle Thread End");
            return v;
        }, executorService);
    }

    public String name(Organization organization){
        return "Custom Scalar Prefix : "  + organization.getName();
    }
}
