package com.finale;

import com.finale.entity.Coach;
import com.finale.entity.CoachRole;
import com.finale.entity.Location;
import com.finale.entity.Student;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.initStudent();
        initService.initLocation();
        initService.initCoach();
    }

    @Component @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void initStudent() {
            Student student1 = Student.builder()
                    .email("test@email.com")
                    .name("황승호")
                    .phoneNumber("010-1122-3344")
                    .build();

            Student student2 = Student.builder()
                    .email("test@email.com")
                    .name("우주형")
                    .phoneNumber("010-1122-3344")
                    .build();

            em.persist(student1);
            em.persist(student2);
        }

        public void initLocation() {
            Location location1 = new Location("계명대학교");
            Location location2 = new Location("고려대학교");

            em.persist(location1);
            em.persist(location2);
        }

        public void initCoach() {
            Coach coach1 = Coach.builder()
                    .name("황승호")
                    .email("test@email.com")
                    .coachRole(CoachRole.MASTER)
                    .build();

            Coach coach2 = Coach.builder()
                    .name("우주형")
                    .email("test2@email.com")
                    .coachRole(CoachRole.SUB)
                    .build();

            em.persist(coach1);
            em.persist(coach2);
        }
    }
}
