package com.finale.lesson.repository;

import com.finale.entity.LessonStudent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonStudentRepository extends JpaRepository<LessonStudent,Long> {
}
