package com.test.graphql.dao.repository;

import com.test.graphql.dao.entity.Vehicle;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class VehicleRepository{
    private final Map<Integer, Vehicle> vehicleMap = new HashMap<>();

    public VehicleRepository(){
        addVehicle(new Vehicle(1, "hatchback", "nano", "tata",
                LocalDate.now(), "2020"));
        addVehicle(new Vehicle(2, "hatchback", "baleon", "maruthi",
                LocalDate.now(), "2015"));
        addVehicle(new Vehicle(3, "sedan", "Amaze", "Honda",
                LocalDate.now(), "2019"));
    }
    public Vehicle addVehicle(Vehicle vehicle){
        vehicleMap.put(vehicle.getId(), vehicle);
        return vehicle;
    }

    public Vehicle getVehicle(Integer id){
        return vehicleMap.get(id);
    }

    public Collection<Vehicle> findAll(){
        return vehicleMap.values();
    }
}
