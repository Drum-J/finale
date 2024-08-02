package com.finale.lesson.repository;

import com.finale.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Optional<Notice> findTopByOrderByCreateAtDesc();
}
