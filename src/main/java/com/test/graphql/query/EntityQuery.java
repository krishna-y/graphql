package com.test.graphql.query;

import com.test.graphql.service.OrganizationService;
import com.test.graphql.service.PersonService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EntityQuery implements GraphQLQueryResolver {

    @Autowired
    private PersonService personService;

    @Autowired
    private OrganizationService organizationService;

    public List<Object> getEntities(final int count) {
        List<Object> list = new ArrayList<>();
        int partition = (int)(Math.random() * count);
        list.addAll(personService.getAllPersons(partition));
        list.addAll(organizationService.getAllOrganizations(count - partition));
        return list;
    }
}
