package com.test.graphql.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.test.graphql.dao.entity.Person;
import com.test.graphql.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class PersonMutation implements GraphQLMutationResolver {

    @Autowired
    private PersonService personService;

    @Transactional
    public Person addPerson(final String name,
                            final List<Integer> vehicles,
                            final List<Integer> houses) {
        return this.personService.createPerson(name, vehicles, houses);
    }
}
