package com.finale.sms;

import com.finale.entity.Notice;
import com.finale.entity.SmsTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SmsTemplateRepository extends JpaRepository<SmsTemplate,Long> {

    Optional<SmsTemplate> findTopByOrderByCreateAtDesc();
}
