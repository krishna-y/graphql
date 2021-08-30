package com.test.graphql;

import com.test.graphql.dao.entity.Vehicle;
import com.test.graphql.dao.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RestApiController {
    private final VehicleRepository vehicleRepository;

    @Autowired
    public RestApiController(VehicleRepository vehicleRepository){
        this.vehicleRepository = vehicleRepository;
    }

    @RequestMapping(value = "/vehicles", method = RequestMethod.POST, produces = "application/json")
    public List<Vehicle> getSummary()
            throws SQLException {
        return new ArrayList<>(vehicleRepository.findAll());
    }
}
