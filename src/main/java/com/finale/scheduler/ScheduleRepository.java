package com.finale.scheduler;

import com.finale.entity.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<EnrollmentStatus, Long> {

}
