package com.test.graphql.service;

import com.test.graphql.dao.entity.Vehicle;
import com.test.graphql.dao.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository ;

    public VehicleService(final VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository ;
    }

    public Vehicle createVehicle(final String type,
                                 final String modelCode,
                                 final String brandName,
                                 final String launchDate) {
        final Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleRepository.findAll().size() + 1);
        vehicle.setType(type);
        vehicle.setModelCode(modelCode);
        vehicle.setBrandName(brandName);
        vehicle.setLaunchDate(LocalDate.parse(launchDate));
        return this.vehicleRepository.addVehicle(vehicle);
    }

    public List<Vehicle> getAllVehicles(final int count) {
        return this.vehicleRepository.findAll().stream().limit(count).collect(Collectors.toList());
    }

    public Optional<Vehicle> getVehicle(final int id) {
        if(this.vehicleRepository.getVehicle(id) == null){
            return Optional.empty();
        } else{
            return Optional.of(vehicleRepository.getVehicle(id));
        }
    }
}
