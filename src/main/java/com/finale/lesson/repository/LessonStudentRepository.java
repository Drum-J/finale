package com.finale.lesson.repository;

import com.finale.entity.LessonStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonStudentRepository extends JpaRepository<LessonStudent,Long> {

    List<LessonStudent> findByStudentId(Long studentId);
}
