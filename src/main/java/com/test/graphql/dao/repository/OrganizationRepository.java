package com.test.graphql.dao.repository;

import com.google.common.collect.ImmutableList;
import com.test.graphql.dao.entity.Organization;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class OrganizationRepository {
    private final Map<Integer, Organization> organizationMap = new HashMap<>();

    public OrganizationRepository(){
        addOrganization(new Organization(1, "Hotstar", ImmutableList.of(1,3)));
        addOrganization(new Organization(2, "Google", ImmutableList.of(2,3)));
        addOrganization(new Organization(3, "Star", ImmutableList.of(1,2)));
        addOrganization(new Organization(4, "Disney", ImmutableList.of(2,1)));
        addOrganization(new Organization(5, "AWS", ImmutableList.of(1,3)));
    }

    public Organization addOrganization(Organization organization){
        organizationMap.put(organization.getId(), organization);
        return organization;
    }

    public Organization getOrganization(Integer id){
        return organizationMap.get(id);
    }

    public List<Organization> findAll(){
        return new ArrayList<>(organizationMap.values());
    }
}
