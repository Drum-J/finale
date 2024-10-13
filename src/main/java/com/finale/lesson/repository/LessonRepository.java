package com.finale.lesson.repository;

import com.finale.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    Optional<Lesson> findByIdAndDelYnIsFalse(Long id);

    List<Lesson> findAllByDelYnIsFalse();

    List<Lesson> findByLessonDateAndDelYnIsFalse(String lessonDate);

    @Modifying
    @Query(value =
            "UPDATE lesson l JOIN timetable t ON l.timetable_id = t.timetable_id" +
            " SET l.del_yn = TRUE WHERE t.location = :locationName"
            ,nativeQuery = true)
    void deleteLessonByLocationName(@Param("locationName") String locationName);
}
