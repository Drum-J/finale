package com.finale.lesson.repository;

import com.finale.entity.QLessonStudent;
import com.finale.lesson.dto.ILessonCoachDTO;
import com.finale.lesson.dto.ILessonDTO;
import com.finale.lesson.dto.QILessonCoachDTO;
import com.finale.lesson.dto.QILessonDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.finale.entity.QLesson.lesson;
import static com.finale.entity.QLessonStudent.*;
import static com.finale.entity.QLocation.location;
import static com.finale.entity.QTimetable.timetable;

@Repository
public class LessonCustomRepository {

    private final JPAQueryFactory query;

    public LessonCustomRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public List<ILessonDTO> getLessonsByLocation(String name) {
        return query
                .select(new QILessonDTO(lesson))
                .from(lesson)
                .join(lesson.timetable, timetable).fetchJoin()
                .join(location)
                .on(location.name.eq(timetable.location)).fetchJoin()
                .where(location.name.eq(name))
                .fetch();
    }

    public List<ILessonCoachDTO> getLessonsByLocationForCoach(String name) {
        return query
                .select(new QILessonCoachDTO(lesson))
                .from(lesson)
                .join(lesson.timetable, timetable).fetchJoin()
                .join(location)
                .on(location.name.eq(timetable.location)).fetchJoin()
                .where(location.name.eq(name))
                .fetch();
    }
}
