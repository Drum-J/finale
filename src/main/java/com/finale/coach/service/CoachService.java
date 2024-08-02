package com.finale.coach.service;

import com.finale.coach.dto.CoachResponseDTO;
import com.finale.coach.dto.CreateNoticeDTO;
import com.finale.coach.repository.CoachRepository;
import com.finale.common.ApiResponse;
import com.finale.entity.Coach;
import com.finale.entity.Lesson;
import com.finale.entity.LessonCoach;
import com.finale.entity.LessonStudent;
import com.finale.entity.Location;
import com.finale.entity.Notice;
import com.finale.entity.Timetable;
import com.finale.exception.ResourceNotFoundException;
import com.finale.lesson.dto.ILessonCoachDTO;
import com.finale.lesson.dto.ILocationDTO;
import com.finale.lesson.dto.TimetableCreateDTO;
import com.finale.lesson.repository.LessonCoachRepository;
import com.finale.lesson.repository.LessonCustomRepository;
import com.finale.lesson.repository.LessonRepository;
import com.finale.lesson.repository.LessonStudentRepository;
import com.finale.lesson.repository.NoticeRepository;
import com.finale.lesson.repository.TimetableRepository;
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
public class CoachService {

    private final CoachRepository coachRepository;
    private final LessonRepository lessonRepository;
    private final LocationRepository locationRepository;
    private final LessonCustomRepository lessonCustomRepository;
    private final LessonStudentRepository lessonStudentRepository;
    private final LessonCoachRepository lessonCoachRepository;
    private final TimetableRepository timetableRepository;
    private final NoticeRepository noticeRepository;

    public ApiResponse getCoachList() {
        return ApiResponse.successResponse(coachRepository.findAll().stream()
                .map(CoachResponseDTO::new).toList());
    }

    @Transactional
    public ApiResponse updateRole(Long id) {
        log.info("=== 코치 권한 변경 Service 진입 ===");
        log.info("=== 코치 ID = {} ===" ,id);

        Coach find = coachRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 코치를 찾을 수 없습니다."));

        find.updateRole();
        return ApiResponse.successResponse("코치 권한이 변경되었습니다.");
    }

    public ApiResponse getAllLessonForCoach() {
        List<Lesson> all = lessonRepository.findAll();
        return ApiResponse.successResponse(all.stream().map(ILessonCoachDTO::new).toList());
    }

    public ApiResponse getLessonsByLocationForCoach(String name) {
        Location location = locationRepository.findByName(name);

        List<ILessonCoachDTO> lessonsByLocation = lessonCustomRepository.getLessonsByLocationForCoach(name);
        return ApiResponse.successResponse(new ILocationDTO<>(location, lessonsByLocation));
    }

    public ApiResponse getLessonDetailsForCoach(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 레슨을 찾을 수 없습니다."));

        return ApiResponse.successResponse(new ILessonCoachDTO(lesson));
    }

    /**
     * 수강생 입금 확인
     * @param id LessonStudent.id 임
     * @return
     */
    @Transactional
    public ApiResponse updateDeposit(Long id) {
        log.info("=== 수강생 입금 확인 Service 진입 ===");
        log.info("=== LessonStudent Id = {} ===",id);

        LessonStudent findStudent = lessonStudentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 수강생을 찾을 수 없습니다."));
        findStudent.depositConfirm();

        return ApiResponse.successResponse("입금 확인이 완료 되었습니다.");
    }

    @Transactional
    public ApiResponse createTimetable(TimetableCreateDTO dto) throws ResourceNotFoundException {
        log.info("=== 레슨 생성 Service 진입 ===");
        log.info("레슨 데이터 = {}",dto);

        Location findLocation = locationRepository.findByName(dto.getLocation());
        if (findLocation == null) {
            throw new ResourceNotFoundException("해당 장소를 찾을 수 없습니다.");
        }

        Timetable timetable = dto.convertToEntity();

        Lesson lesson = new Lesson(timetable);

        List<Long> coachId = dto.getCoaches();
        coachId.stream().map(id -> coachRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("해당 코치를 찾을 수 없습니다.")))
                .map(coach -> {
                            LessonCoach lessonCoach = new LessonCoach(lesson, coach);
                            lessonCoachRepository.save(lessonCoach);

                            return lessonCoach;
                        }
                ).forEach(lesson::addCoaches);

        timetableRepository.save(timetable);
        lessonRepository.save(lesson);

        return ApiResponse.successResponse("레슨 생성을 완료했습니다.");
    }

    @Transactional
    public ApiResponse createNotice(CreateNoticeDTO dto) {
        Notice notice = dto.toEntity();

        noticeRepository.save(notice);

        return ApiResponse.successResponse("레슨 공지사항 생성을 완료했습니다.");
    }
}
