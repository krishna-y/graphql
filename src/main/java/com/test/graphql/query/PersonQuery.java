package com.test.graphql.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.test.graphql.dao.entity.Person;
import com.test.graphql.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonQuery implements GraphQLQueryResolver {

    @Autowired
    private PersonService personService;

    public List<Person> getPersons(final int count) {
        return this.personService.getAllPersons(count);
    }

    public Optional<Person> getPerson(final int id) {
        return this.personService.getPerson(id);
    }
}
