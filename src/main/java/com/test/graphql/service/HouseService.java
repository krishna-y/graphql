package com.test.graphql.service;

import com.test.graphql.dao.entity.House;
import com.test.graphql.dao.repository.HouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HouseService {

    private final HouseRepository houseRepository ;

    public HouseService(final HouseRepository houseRepository) {
        this.houseRepository = houseRepository ;
    }

    public House createHouse(final String city,
                             final String address) {
        final House house = new House();
        house.setId(houseRepository.findAll().size() + 1);
        house.setCity(city);
        house.setAddress(address);
        return this.houseRepository.addHouse(house);
    }

    public List<House> getAllHouses(final int count) {
        return this.houseRepository.findAll().stream().limit(count).collect(Collectors.toList());
    }

    public Optional<House> getHouse(final int id) {
        if(this.houseRepository.getHouse(id) == null){
            return Optional.empty();
        } else{
            return Optional.of(houseRepository.getHouse(id));
        }
    }
}
