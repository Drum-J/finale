package com.finale.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Location extends TimeStamped {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "location_id")
    private Long id;

    private String name;

    private String city;

    private String district;

    private String address;

    public Location(String name, String city, String district, String address) {
        this.name = name;
        this.city = city;
        this.district = district;
        this.address = address;
    }
}
