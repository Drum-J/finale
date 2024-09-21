package com.finale.sms;

import com.finale.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sms")
public class SmsController {

    private final SmsService smsService;

    @PostMapping("/remind")
    public ApiResponse remindSend(@RequestBody RemindDTO dto) throws CoolsmsException {
        return smsService.remindSend(dto);
    }

    @PostMapping("/createTemplate")
    public ApiResponse create(@RequestBody TemplateDTO dto) {
        return smsService.create(dto.text());
    }

    @GetMapping("/detail")
    public ApiResponse detail() {
        return smsService.getDetail();
    }
}
