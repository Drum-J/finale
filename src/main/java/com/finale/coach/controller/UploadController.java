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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/upload")
public class UploadController {

    private final UploadService uploadService;

    @PostMapping("/timetable")
    public ApiResponse timetableUpload(@RequestPart(value = "file", required = false) MultipartFile file) {

        log.info("==== 시간표 업로드 Controller 진입 ====");
        log.info("전달받은 시간표 이미지 = {}", file);

        if (file != null && !file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();

            if (!FileValidator.isImage(originalFilename)) {
                throw new IllegalStateException("이미지 확장자를 확인해 주세요. [jpg,jpeg,png,gif] 파일만 가능합니다.");
            }
        }

        /*
        if (dto.id() == null) {
            return uploadService.timetableUpload(dto);
        } else {
            return uploadService.timetableUpdate(dto);
        }
        */
        S3UploadDTO dto = new S3UploadDTO(null, null, file);

        return uploadService.timetableUpload(dto);
    }
}
