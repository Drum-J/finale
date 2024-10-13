package com.finale.sms;

import com.finale.entity.SmsTemplate;
import com.finale.entity.SmsType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SmsTemplateRepository extends JpaRepository<SmsTemplate,Long> {

    Optional<SmsTemplate> findTopByOrderByCreateAtDesc();

    Optional<SmsTemplate> findBySmsType(SmsType type);
}
