package com.finale.student.repository;

import com.finale.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Long> {
    Optional<Student> findByNameAndEmailAndPhoneNumber(String name, String email, String phoneNumber);
}
