package com.test.graphql.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "vehicleIds", nullable = false)
    @ElementCollection(targetClass=Integer.class)
    private List<Integer> vehicleIds;

    @Column(name = "houseIds", nullable = false)
    @ElementCollection(targetClass=Integer.class)
    private List<Integer> houseIds;
}
