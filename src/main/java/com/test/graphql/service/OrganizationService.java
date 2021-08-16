package com.test.graphql.service;

import com.test.graphql.dao.entity.Organization;
import com.test.graphql.dao.repository.HouseRepository;
import com.test.graphql.dao.repository.OrganizationRepository;
import com.test.graphql.dao.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final VehicleRepository vehicleRepository;
    private final HouseRepository houseRepository;

    @Autowired
    public OrganizationService(final OrganizationRepository organizationRepository,
                               final VehicleRepository vehicleRepository,
                               final HouseRepository houseRepository) {
        this.organizationRepository = organizationRepository;
        this.vehicleRepository = vehicleRepository;
        this.houseRepository = houseRepository;
    }

    public Organization createOrganization(final String name,
                                           final List<Integer> vehicles) {
        final Organization organization = new Organization();
        organization.setId(organizationRepository.findAll().size() + 1);
        organization.setName(name);
        organization.setVehicleIds(vehicles);
        return organization;
    }

    public List<Organization> getAllOrganizations(final int count) {
        return this.organizationRepository.findAll().stream().limit(count).collect(Collectors.toList());
    }
}
