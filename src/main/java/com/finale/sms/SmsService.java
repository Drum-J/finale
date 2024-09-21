package com.finale.sms;

import com.finale.common.ApiResponse;
import com.finale.entity.LessonStudent;
import com.finale.entity.SmsTemplate;
import com.finale.entity.Student;
import com.finale.entity.Timetable;
import com.finale.exception.ResourceNotFoundException;
import com.finale.lesson.repository.LessonStudentRepository;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class SmsService {

    @Value("${coolsms.api.key}")
    private String apiKey;

    @Value("${coolsms.api.secret}")
    private String apiSecretKey;

    @Value("${coolsms.api.phoneNumber}")
    private String phoneNumber;

    @Value("${sms.templates.apply}")
    private String applyTemplate;

    @Value("${sms.templates.cancel}")
    private String cancelTemplate;

    @Value("${sms.templates.remind}")
    private String remindTemplate;

    private final LessonStudentRepository lessonStudentRepository;
    private final SmsTemplateRepository smsTemplateRepository;

    /**
     * 레슨 신청 시 문자 발송
     */
    public void applySend(Timetable timetable, Student student) throws CoolsmsException {
        sendSms(applyTemplate, student, timetable);
    }

    /**
     * 레슨 취소 시 문자 발송
     */
    public void cancelSend(Timetable timetable, Student student) throws CoolsmsException {
        sendSms(cancelTemplate,student, timetable);
    }

    /**
     * 입금 확인 문자 발송
     */
    public void depositSend(Timetable timetable, Student student) throws CoolsmsException {
        SmsTemplate smsTemplate = smsTemplateRepository.findTopByOrderByCreateAtDesc()
                .orElseThrow(() -> new ResourceNotFoundException("저장된 문자 형식이 없습니다."));
        sendSms(smsTemplate.getText(), student, timetable);
    }

    /**
     * 입금 독촉 문자 발송
     */
    @Transactional
    public ApiResponse remindSend(RemindDTO dto) throws CoolsmsException {
        LessonStudent lessonStudent = lessonStudentRepository.findById(dto.getLessonStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("해당 레슨 내역을 찾을 수 없습니다."));

        lessonStudent.sendRemind();

        Message remindMessage = new Message(apiKey, apiSecretKey);

        HashMap<String, String> params = new HashMap<>();
        params.put("to", formatPhoneNumber(dto.getPhoneNumber()));
        params.put("from", phoneNumber);
        params.put("type", "LMS");
        params.put("subject", "[피겨 피날레]");
        params.put("text", remindTemplate);

        remindMessage.send(params);

        return ApiResponse.successResponse("문자 발송을 성공했습니다.");
    }

    private void sendSms(String smsTemplate, Student student, Timetable timetable) throws CoolsmsException {
        Message message = new Message(apiKey, apiSecretKey);

        HashMap<String, String> params = new HashMap<>();
        params.put("to", formatPhoneNumber(student.getPhoneNumber()));
        params.put("from", phoneNumber);
        params.put("type", "LMS");
        params.put("subject", "[피겨 피날레]");
        params.put("text", smsFormatter(smsTemplate, timetable, student));

        message.send(params);
    }

    private String formatPhoneNumber(String phoneNumber) {
       return phoneNumber.replace("+82 ", "0").replaceAll("-", "");
    }

    private static String getDay(int days) {
        String day = "";
        switch (days) {
            case 1 -> day = "월요일";
            case 2 -> day = "화요일";
            case 3 -> day = "수요일";
            case 4 -> day = "목요일";
            case 5 -> day = "금요일";
            case 6 -> day = "토요일";
            case 7 -> day = "일요일";
        }
        return day;
    }

    private String smsFormatter(String template, Timetable timetable, Student student) {
        String day = getDay(timetable.getDays());

        return template
                .replace("{name}",student.getName())
                .replace("{location}",timetable.getLocation())
                .replace("{days}", day)
                .replace("{startTime}", timetable.getStartTime())
                .replace("{endTime}", timetable.getEndTime())
                .replace("{cost}", timetable.getCost());
    }

    @Transactional
    public ApiResponse create(String content) {
        SmsTemplate smsTemplate = new SmsTemplate(content);
        smsTemplateRepository.save(smsTemplate);

        return ApiResponse.successResponse("문자 문구를 저장했습니다.");
    }

    public ApiResponse getDetail() {
        SmsTemplate smsTemplate = smsTemplateRepository.findTopByOrderByCreateAtDesc()
                .orElseThrow(() -> new ResourceNotFoundException("저장된 문자 형식이 없습니다."));
        return ApiResponse.successResponse(smsTemplate.getText());
    }

}
