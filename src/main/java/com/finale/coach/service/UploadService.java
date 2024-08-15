package com.finale.coach.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.finale.coach.dto.S3UploadDTO;
import com.finale.coach.repository.CoachRepository;
import com.finale.common.ApiResponse;
import com.finale.entity.Coach;
import com.finale.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UploadService {

    private final AmazonS3Client s3Client;
    private final CoachRepository coachRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private static final String COACH_IMG = "coach/";
    private static final String TIMETABLE_IMG = "timetable/";

    public ApiResponse coachProfileUpload(S3UploadDTO dto) {
        log.info("===== 코치 이미지 업로드 Service 진입 =====");

        Long coachId = dto.id();
        log.info("코치 ID = {}",coachId);

        Coach coach = coachRepository.findById(coachId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 코치를 찾을 수 없습니다."));

        String oldProfile = coach.getProfile();

        // 기존 프로필이 있다면 S3에서 해당 이미지 삭제
        if (StringUtils.hasText(oldProfile)) {
            log.info("기존 이미지 = {} ",oldProfile);
            deleteFile(oldProfile);
        }

        // 새로운 이미지 등록
        try {
            MultipartFile file = dto.file();

            String fileName = file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            String contentType = file.getContentType();
            long size = file.getSize();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);
            metadata.setContentLength(size);

            s3Client.putObject(bucket, COACH_IMG + fileName, inputStream,metadata);

            URL imageUrl = s3Client.getUrl(bucket, COACH_IMG + fileName);

            coach.updateProfile(imageUrl.toString());

            return ApiResponse.successResponse("이미지 업로드 성공 : " + imageUrl);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void deleteFile(String oldProfile) {
        s3Client.deleteObject(bucket,oldProfile);
    }
}
