package com.test.graphql.context;

import com.test.graphql.dao.entity.Person;
import com.test.graphql.dao.repository.PersonRepository;
import org.dataloader.BatchLoader;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class DataLoaderRegistryFactory {
    private PersonRepository personRepository;
    ExecutorService executorService = Executors.newFixedThreadPool(4);

    @Autowired
    DataLoaderRegistryFactory(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    public DataLoaderRegistry create(){
        DataLoaderRegistry dataLoaderRegistry = new DataLoaderRegistry();
        dataLoaderRegistry.register("PERSON_DATA_LOADER", createPersonDataLoader());
        return dataLoaderRegistry;
    }

    private DataLoader<Integer, Person> createPersonDataLoader() {
        return DataLoader.newDataLoader(this::load);
    }

    private CompletionStage<List<Person>> load(List<Integer> list) {
        return CompletableFuture.supplyAsync(()-> {
            System.out.println(list);
            return new ArrayList<>(personRepository.findPersons(list));
        }, executorService);
    }
}
