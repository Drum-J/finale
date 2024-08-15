package com.finale.coach.controller;

import com.finale.coach.dto.S3UploadDTO;
import com.finale.coach.service.UploadService;
import com.finale.common.ApiResponse;
import com.finale.common.FileValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/upload")
public class UploadController {

    private final UploadService uploadService;

    @PostMapping("/timetable")
    public ApiResponse timetableUpload(@ModelAttribute S3UploadDTO dto) {
        String originalFilename = dto.file().getOriginalFilename();

        if (!FileValidator.isImage(originalFilename)) {
            throw new IllegalStateException("이미지 확장자를 확인해 주세요. [jpg,jpeg,png,gif] 파일만 가능합니다.");
        }

        /*
        if (dto.id() == null) {
            return uploadService.timetableUpload(dto);
        } else {
            return uploadService.timetableUpdate(dto);
        }
        */

        return uploadService.timetableUpload(dto);
    }
}
