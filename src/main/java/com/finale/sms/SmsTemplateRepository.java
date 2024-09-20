package com.finale.sms;

import com.finale.entity.SmsTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsTemplateRepository extends JpaRepository<SmsTemplate,Long> {
}
