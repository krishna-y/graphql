package com.test.graphql.query;

import com.test.graphql.dao.entity.House;
import com.test.graphql.service.HouseService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class HouseQuery implements GraphQLQueryResolver {

    @Autowired
    private HouseService houseService;

    public List<House> getHouses(final int count) {
        return this.houseService.getAllHouses(count);
    }


    public Optional<House> getHouse(final int id) {
        return this.houseService.getHouse(id);
    }
}
