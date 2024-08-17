package com.finale.admin.dto;

import com.querydsl.core.annotations.QueryProjection;

public record DepositResponseDTO(Long lessonStudentId, Long studentId,
                                 String lessonTitle, String studentName,
                                 String phoneNumber, String enrollmentDate) {

    @QueryProjection
    public DepositResponseDTO {
    }
}
