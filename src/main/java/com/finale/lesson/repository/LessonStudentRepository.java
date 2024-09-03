package com.finale.lesson.repository;

import com.finale.entity.LessonStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessonStudentRepository extends JpaRepository<LessonStudent,Long> {

    List<LessonStudent> findByStudentId(Long studentId);

    @Query("SELECT DISTINCT l.student.id FROM LessonStudent l WHERE l.deposit = true")
    List<Long> findStudentIdByDepositTrue();
}
