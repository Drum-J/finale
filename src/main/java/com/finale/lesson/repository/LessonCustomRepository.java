package com.finale.lesson.repository;

import com.finale.common.DateUtil;
import com.finale.lesson.dto.ILessonCoachDTO;
import com.finale.lesson.dto.ILessonDTO;
import com.finale.lesson.dto.QILessonCoachDTO;
import com.finale.lesson.dto.QILessonDTO;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.finale.entity.QLesson.lesson;
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
                .where(location.name.eq(name),isWithinMonth(timetable.date))
                .fetch();
    }

    public List<ILessonCoachDTO> getLessonsByLocationForCoach(String name) {
        return query
                .select(new QILessonCoachDTO(lesson))
                .from(lesson)
                .join(lesson.timetable, timetable).fetchJoin()
                .join(location)
                .on(location.name.eq(timetable.location)).fetchJoin()
                .where(location.name.eq(name),isWithinMonth(timetable.date))
                .fetch();
    }

    private BooleanExpression isWithinMonth(StringPath date) {
        LocalDate firstDay = DateUtil.getFirstDayOfMonth();
        LocalDate lastDay = DateUtil.getLastDayOfMonth();
        return date.goe(firstDay.toString()).and(date.loe(lastDay.toString()));
    }
}
