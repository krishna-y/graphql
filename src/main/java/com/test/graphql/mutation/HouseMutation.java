package com.test.graphql.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.test.graphql.dao.entity.House;
import com.test.graphql.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HouseMutation implements GraphQLMutationResolver {

    @Autowired
    private HouseService houseService;

    public House createHouse(final String city,
                             final String address) {
        return this.houseService.createHouse(city, address);
    }
}
