package com.test.graphql.dao.repository;

import com.test.graphql.dao.entity.House;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class HouseRepository {
    private final Map<Integer, House> houseMap = new HashMap<>();

    public HouseRepository(){
        addHouse(new House(1, "Guntur", "Address 1"));
        addHouse(new House(2, "Bangalore", "Address 2"));
        addHouse(new House(3, "Chicago", "Address 3"));
    }

    public House addHouse(House house){
        houseMap.put(house.getId(), house);
        return house;
    }

    public House getHouse(Integer id){
        return houseMap.get(id);
    }

    public Collection<House> findAll(){
        return houseMap.values();
    }
}
