package com.finale.lesson.repository;

import com.finale.admin.dto.DepositRequestDTO;
import com.finale.admin.dto.DepositResponseDTO;
import com.finale.admin.dto.QDepositResponseDTO;
import com.finale.coach.dto.EnrollmentResponseDTO;
import com.finale.coach.dto.EnrollmentSearchDTO;
import com.finale.coach.dto.QEnrollmentResponseDTO;
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
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

import static com.finale.entity.QLesson.lesson;
import static com.finale.entity.QLessonStudent.lessonStudent;
import static com.finale.entity.QLocation.location;
import static com.finale.entity.QStudent.student;
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

    public List<EnrollmentResponseDTO> getEnrollmentList(EnrollmentSearchDTO dto) {
        return query
                .select(new QEnrollmentResponseDTO(
                        lessonStudent.lesson.id,
                        lessonStudent.lesson.timetable.location,
                        lessonStudent.lesson.lessonDate,
                        lessonStudent.lesson.timetable.days,
                        lessonStudent.student.id,
                        lessonStudent.student.name,
                        lessonStudent.student.phoneNumber ,
                        lessonStudent.deposit,
                        lessonStudent.restLesson
                        ))
                .from(lessonStudent)
                .where(
                        lessonDateEq(dto.lessonDate())
                )
                .orderBy(lessonStudent.lesson.id.asc(),lessonStudent.student.id.asc())
                .fetch();
    }

    public List<DepositResponseDTO> getDepositList(DepositRequestDTO dto, boolean isDeposit) {
        return query
                .select(new QDepositResponseDTO(
                        lessonStudent.id,
                        lessonStudent.student.id,
                        lessonStudent.lesson.title,
                        lessonStudent.student.name,
                        lessonStudent.student.phoneNumber,
                        lessonStudent.createAt.stringValue()
                ))
                .from(lessonStudent)
                .where(
                        lessonDateEq(dto.date()),
                        studentNameEq(dto.studentName()),
                        locationEq(dto.location()),
                        lessonStudent.deposit.eq(isDeposit),
                        lessonStudent.restLesson.eq(false)
                )
                .fetch();
    }

    private BooleanExpression lessonDateEq(String lessonDate) {
        return StringUtils.hasText(lessonDate) ? lesson.lessonDate.eq(lessonDate) : null;
    }

    private BooleanExpression studentNameEq(String name) {
        return StringUtils.hasText(name) ? student.name.eq(name) : null;
    }

    private BooleanExpression locationEq(String location) {
        return StringUtils.hasText(location) ? lesson.timetable.location.eq(location) : null;
    }
}
