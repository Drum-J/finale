package com.finale.coach.repository;

import com.finale.entity.Coach;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoachRepository extends JpaRepository<Coach,Long> {
    Optional<Coach> findByNameAndEmailAndPhoneNumber(String name, String email, String phoneNumber);
}
