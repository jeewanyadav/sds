package com.batteries.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
@Data
public class Battery {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String postcode;
    private int capacity;

    public Battery(String name, String postcode, int i) {
    }

    public Battery() {

    }
}
