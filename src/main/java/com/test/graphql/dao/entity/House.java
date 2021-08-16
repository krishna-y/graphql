package com.test.graphql.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class House implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "address", nullable = false)
    private String address;
}
