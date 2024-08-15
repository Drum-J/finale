package com.finale.coach.repository;

import com.finale.entity.TimetableImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TimetableImageRepository extends JpaRepository<TimetableImage, Long> {
    Optional<TimetableImage> findTopByOrderByCreateAtDesc();
}
