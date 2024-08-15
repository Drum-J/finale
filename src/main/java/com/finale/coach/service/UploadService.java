package com.finale.coach.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.finale.coach.dto.S3UploadDTO;
import com.finale.coach.repository.CoachRepository;
import com.finale.coach.repository.TimetableImageRepository;
import com.finale.common.ApiResponse;
import com.finale.entity.Coach;
import com.finale.entity.TimetableImage;
import com.finale.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UploadService {

    private final AmazonS3Client s3Client;
    private final CoachRepository coachRepository;
    private final TimetableImageRepository timetableImageRepository;

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

        String oldProfile = coach.getS3Key();

        // 기존 프로필이 있다면 S3에서 해당 이미지 삭제
        if (StringUtils.hasText(oldProfile)) {
            log.info("기존 이미지 = {} ",oldProfile);
            deleteFile(oldProfile);
        }

        // 새로운 이미지 등록
        try {
            String imageUrl = upload(dto,COACH_IMG);

            coach.updateProfile(imageUrl,COACH_IMG + dto.file().getOriginalFilename());

            return ApiResponse.successResponse("이미지 업로드 성공 : " + imageUrl);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public ApiResponse timetableUpload(S3UploadDTO dto) {
        log.info("===== 시간표 이미지 업로드 Service 진입 =====");

        try {
            String imageUrl = upload(dto, TIMETABLE_IMG);
            TimetableImage timetableImage = new TimetableImage(imageUrl, TIMETABLE_IMG + dto.file().getOriginalFilename());
            timetableImageRepository.save(timetableImage);

            return ApiResponse.successResponse("이미지 등록 성공");
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public ApiResponse timetableUpdate(S3UploadDTO dto) {
        log.info("===== 시간표 이미지 수정 Service 진입 =====");

        TimetableImage timetableImage = timetableImageRepository.findById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException("해당 시간표 이미지를 찾을 수 없습니다."));

        String oldImage = timetableImage.getS3Key();

        // 기존 이미지가 있다면 S3에서 해당 이미지 삭제
        if (StringUtils.hasText(oldImage)) {
            log.info("기존 이미지 = {} ",oldImage);
            deleteFile(oldImage);
        }

        try {
            String imageUrl = upload(dto, TIMETABLE_IMG);

            timetableImage.updateImage(imageUrl, TIMETABLE_IMG + dto.file().getOriginalFilename());

            return ApiResponse.successResponse("이미지 수정 성공 : " + imageUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String upload(S3UploadDTO dto,String type) throws IOException {
        MultipartFile file = dto.file();

        String fileName = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        String contentType = file.getContentType();
        long size = file.getSize();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        metadata.setContentLength(size);

        s3Client.putObject(bucket, type + fileName, inputStream,metadata);

        return s3Client.getUrl(bucket, type + fileName).toString();
    }

    private void deleteFile(String oldProfile) {
        s3Client.deleteObject(bucket,oldProfile);
    }
}
