package com.test.graphql.mutation;

import com.test.graphql.dao.entity.Organization;
import com.test.graphql.service.OrganizationService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class OrganizationMutation implements GraphQLMutationResolver {

    @Autowired
    private OrganizationService organizationService;

    @Transactional
    public Organization addOrganization(final String name,
                                        final List<Integer> vehicles) {
        return this.organizationService.createOrganization(name, vehicles);
    }
}
