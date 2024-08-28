package com.finale.lesson.service;

import com.finale.coach.repository.TimetableImageRepository;
import com.finale.common.ApiResponse;
import com.finale.entity.Lesson;
import com.finale.entity.Location;
import com.finale.entity.Notice;
import com.finale.entity.TimetableImage;
import com.finale.exception.ResourceNotFoundException;
import com.finale.lesson.dto.ILessonDTO;
import com.finale.lesson.dto.ILocationDTO;
import com.finale.lesson.dto.LessonWithDateAndLocationDTO;
import com.finale.lesson.repository.LessonCustomRepository;
import com.finale.lesson.repository.LessonRepository;
import com.finale.lesson.repository.NoticeRepository;
import com.finale.location.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LocationRepository locationRepository;
    private final LessonCustomRepository lessonCustomRepository;
    private final NoticeRepository noticeRepository;
    private final TimetableImageRepository timetableImageRepository;

    public ApiResponse getLessonDetails(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 레슨을 찾을 수 없습니다."));

        return ApiResponse.successResponse(new ILessonDTO(lesson));

    }

    public ApiResponse getAllLesson() {
        List<Lesson> all = lessonRepository.findAll();
        return ApiResponse.successResponse(all.stream().map(ILessonDTO::new).toList());
    }

    /**
     * 장소 별 레슨 조회 - 수강생 용
     * @param name
     * @param type
     * @return
     */
    public ApiResponse getLessonsByLocation(String name) {
        Location location = locationRepository.findByName(name);

        List<ILessonDTO> lessonsByLocation = lessonCustomRepository.getLessonsByLocation(name);
        return ApiResponse.successResponse(new ILocationDTO<>(location, lessonsByLocation));
    }

    public ApiResponse getNotice() {
        Notice notice = noticeRepository.findTopByOrderByCreateAtDesc()
                .orElseThrow(() -> new ResourceNotFoundException("아직 공지사항이 없습니다."));

        return ApiResponse.successResponse(notice.getContents());
    }

    public ApiResponse getTimetable() {
        TimetableImage timetableImage = timetableImageRepository.findTopByOrderByCreateAtDesc()
                .orElseThrow(() -> new ResourceNotFoundException("아직 시간표를 등록하지 않았습니다."));

        return ApiResponse.successResponse(timetableImage.getUrl());
    }

    public ApiResponse getLessonWithDateAndLocation(String date, String location) {
        List<LessonWithDateAndLocationDTO> result = lessonCustomRepository.getLessonWithDateAndLocation(date, location);

        return ApiResponse.successResponse(result);
    }
}
