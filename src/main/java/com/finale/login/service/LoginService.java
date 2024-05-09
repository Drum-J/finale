package com.finale.login.service;

import com.finale.coach.repository.CoachRepository;
import com.finale.entity.Coach;
import com.finale.entity.CoachRole;
import com.finale.entity.Student;
import com.finale.jwt.JwtProvider;
import com.finale.login.dto.KakaoUserInfo;
import com.finale.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final CoachRepository coachRepository;
    private final StudentRepository studentRepository;
    private final JwtProvider jwtProvider;

    public String loginCoach(KakaoUserInfo coachInfo) {
        Coach coach = coachRepository.findByNameAndEmailAndPhoneNumber(
                coachInfo.name(), coachInfo.email(), coachInfo.phoneNumber()).orElse(null);

        if (coach == null) {
            Coach saveCoach = Coach.builder()
                    .name(coachInfo.name())
                    .email(coachInfo.email())
                    .phoneNumber(coachInfo.phoneNumber())
                    .coachRole(CoachRole.SUB)
                    .build();

            coach = coachRepository.save(saveCoach);
        }

        return jwtProvider.createAccessToken(coach.getId(), coach.getName(), coach.getCoachRole().toString());
    }

    public String loginStudent(KakaoUserInfo studentInfo) {
        Student student = studentRepository.findByNameAndEmailAndPhoneNumber(
                studentInfo.name(), studentInfo.email(), studentInfo.phoneNumber()).orElse(null);

        if (student == null) {
            Student saveStudent = Student.builder()
                    .name(studentInfo.name())
                    .email(studentInfo.email())
                    .phoneNumber(studentInfo.phoneNumber())
                    .build();
            student = studentRepository.save(saveStudent);
        }

        return jwtProvider.createAccessToken(student.getId(), student.getName(), "STUDENT");
    }
}
