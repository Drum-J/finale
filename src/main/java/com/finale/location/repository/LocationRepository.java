package com.finale.location.repository;

import com.finale.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location,Long> {
    Location findByName(String name);
}
