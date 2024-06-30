package com.finale.lesson.dto;

import com.finale.entity.Location;
import lombok.Getter;

import java.util.List;

@Getter
public class ILocationDTO<T> {
    private final Long id;
    private final String name;
    private final String city;
    private final String district;
    private final String address;
    private final List<T> lessons;

    public ILocationDTO(Location location, List<T> lessons) {
        this.id = location.getId();
        this.name = location.getName();
        this.city = location.getCity();
        this.district = location.getDistrict();
        this.address = location.getAddress();
        this.lessons = lessons;
    }
}
