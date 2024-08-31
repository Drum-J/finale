package com.finale.student.repository;

import com.finale.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Long> {
    Optional<Student> findByNameAndEmailAndPhoneNumber(String name, String email, String phoneNumber);

    @Query("UPDATE Student s SET s.newbie = false WHERE s.id IN :studentId")
    void updateNewbie(List<Long> studentId);
}
