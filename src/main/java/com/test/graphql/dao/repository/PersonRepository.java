package com.test.graphql.dao.repository;

import com.google.common.collect.ImmutableList;
import com.test.graphql.dao.entity.Person;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class PersonRepository{
    private final Map<Integer, Person> personMap = new HashMap<>();

    public PersonRepository(){
        addPerson(new Person(1, "Krishna", ImmutableList.of(2,3), ImmutableList.of(1,2)));
        addPerson(new Person(2, "Rahul", ImmutableList.of(1,3), ImmutableList.of(3,2)));
        addPerson(new Person(3, "Bhanu", ImmutableList.of(2,1), ImmutableList.of(1,2)));
        addPerson(new Person(4, "Sunil", ImmutableList.of(3,2), ImmutableList.of(1,3)));
        addPerson(new Person(5, "Sumanth", ImmutableList.of(1,2), ImmutableList.of(1,2)));
    }

    public Person addPerson(Person person){
        personMap.put(person.getId(), person);
        return person;
    }

    public Person getPerson(Integer id){
        return personMap.get(id);
    }

    public Collection<Person> findAll(){
        return personMap.values();
    }
}
