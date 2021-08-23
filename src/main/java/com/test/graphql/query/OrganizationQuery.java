package com.test.graphql.query;

import com.test.graphql.dao.entity.Organization;
import com.test.graphql.dao.repository.OrganizationRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrganizationQuery implements GraphQLQueryResolver {

    @Autowired
    public OrganizationRepository organizationRepository;

    public Organization getOrganization(final int id) {
        return organizationRepository.getOrganization(id);
    }
}
