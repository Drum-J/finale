package com.finale.coach.dto;

import org.springframework.web.multipart.MultipartFile;

/**
 * @param id coach ID 값 or timetable_image ID 값(timetable_image 테이블 생성 필요)
 */
public record S3UploadDTO(Long id, MultipartFile file) {

}
