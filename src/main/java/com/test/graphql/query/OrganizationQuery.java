package com.test.graphql.query;

import com.test.graphql.dao.entity.Organization;
import com.test.graphql.dao.repository.OrganizationRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrganizationQuery implements GraphQLQueryResolver {

    public OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationQuery(OrganizationRepository organizationRepository){
        this.organizationRepository = organizationRepository;
    }

    public Organization getOrganization(final int id) {
        return organizationRepository.getOrganization(id);
    }

    public DataFetcher getOrganizations() {
        return dataFetchingEnvironment -> this.organizationRepository.findAll();
    }
}
