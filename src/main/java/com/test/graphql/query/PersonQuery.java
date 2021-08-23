package com.test.graphql.query;

import com.test.graphql.dao.entity.Person;
import com.test.graphql.service.PersonService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class PersonQuery implements GraphQLQueryResolver {

    @Autowired
    private PersonService personService;

    public List<Person> getPersons(final int count, DataFetchingEnvironment dataFetchingEnvironment) {
        return personService.getAllPersons(count);
    }

    public CompletableFuture<Person> getPerson(final int id, DataFetchingEnvironment dataFetchingEnvironment) {
        DataLoader<Integer, Person> person_data_loader = dataFetchingEnvironment
                .getDataLoader("PERSON_DATA_LOADER");
        return person_data_loader.load(id);
    }
}
