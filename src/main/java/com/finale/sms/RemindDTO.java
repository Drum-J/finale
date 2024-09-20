package com.finale.sms;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class RemindDTO {

    private Long lessonStudentId;
    private String phoneNumber;

    public RemindDTO(Long lessonStudentId, String phoneNumber) {
        this.lessonStudentId = lessonStudentId;
        this.phoneNumber = phoneNumber;
    }
}
