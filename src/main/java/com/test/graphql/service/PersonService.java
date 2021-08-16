package com.test.graphql.service;

import com.test.graphql.dao.entity.Person;
import com.test.graphql.dao.repository.HouseRepository;
import com.test.graphql.dao.repository.PersonRepository;
import com.test.graphql.dao.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository ;
    private final VehicleRepository vehicleRepository ;
    private final HouseRepository houseRepository ;

    @Autowired
    public PersonService(final PersonRepository personRepository,
                         final VehicleRepository vehicleRepository,
                         final HouseRepository houseRepository) {
        this.personRepository = personRepository ;
        this.vehicleRepository = vehicleRepository;
        this.houseRepository = houseRepository;
    }

    public Person createPerson(final String name,
                               final List<Integer> vehicles,
                               final List<Integer> houses) {
        final Person person = new Person();
        person.setId(personRepository.findAll().size() + 1);
        person.setName(name);
        person.setVehicleIds(vehicles);
        person.setHouseIds(vehicles);
        personRepository.addPerson(person);
        return person;
    }

    public List<Person> getAllPersons(final int count) {
        return this.personRepository.findAll().stream().limit(count).collect(Collectors.toList());
    }


    public Optional<Person> getPerson(final int id) {
        if(this.personRepository.getPerson(id) == null){
            return Optional.empty();
        } else{
            return Optional.of(personRepository.getPerson(id));
        }
    }
}
