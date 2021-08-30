package com.test.graphql.resolvers;

import com.test.graphql.context.DataLoaderRegistryFactory;
import com.test.graphql.dao.entity.House;
import com.test.graphql.dao.entity.Person;
import com.test.graphql.dao.entity.Vehicle;
import com.test.graphql.dao.repository.HouseRepository;
import com.test.graphql.dao.repository.PersonRepository;
import com.test.graphql.dao.repository.VehicleRepository;
import graphql.GraphQL;
import graphql.kickstart.tools.GraphQLResolver;
import graphql.schema.DataFetchingEnvironment;
import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class PersonResolver implements GraphQLResolver<Person> {
    private final PersonRepository personRepository;
    private final VehicleRepository vehicleRepository;
    private final HouseRepository houseRepository;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Autowired
    public PersonResolver(PersonRepository personRepository,
                          VehicleRepository vehicleRepository,
                          HouseRepository houseRepository) {
        this.personRepository = personRepository;
        this.vehicleRepository = vehicleRepository;
        this.houseRepository = houseRepository;
    }

    public CompletableFuture<List<Person>> friends(Person person,
                                                   DataFetchingEnvironment dataFetchingEnvironment){
//        System.out.println("Friends Thread + " + person.getName());
        DataLoader<Integer, Person> person_data_loader = dataFetchingEnvironment
                .getDataLoader("PERSON_DATA_LOADER");
        return person_data_loader.loadMany(person.getFriendIds());
    }

    public CompletableFuture<List<Vehicle>> vehicles(Person person) {
        return CompletableFuture.supplyAsync(() -> {
//            System.out.println(Thread.currentThread().getName() + "Person Vehicle Thread Start");
            List<Integer> vehicleIds = person.getVehicleIds();
            List<Vehicle> v = new ArrayList<>();
            for (int i = 0; i < vehicleIds.size(); i++) {
                v.add(vehicleRepository.getVehicle(vehicleIds.get(i)));
            }
//            System.out.println(Thread.currentThread().getName() + "Persone Vehicle Thread End");
            return v;
        }, executorService);
    }

    public CompletableFuture<List<House>> houses(Person person) {

        return CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "Person House Thread Start");
            List<Integer> houseIds = person.getHouseIds();
            List<House> h = new ArrayList<>();
            for (int i = 0; i < houseIds.size(); i++) {
                h.add(houseRepository.getHouse(houseIds.get(i)));
            }
            System.out.println(Thread.currentThread().getName() + "Person House Thread End");
            return h;
        });
    }
}
