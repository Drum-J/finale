package com.finale.lesson.repository;

import com.finale.entity.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimetableRepository extends JpaRepository<Timetable,Long> {
}
