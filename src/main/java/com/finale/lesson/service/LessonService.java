package com.finale.lesson.service;

import com.finale.coach.repository.CoachRepository;
import com.finale.common.ApiResponse;
import com.finale.entity.Lesson;
import com.finale.entity.LessonCoach;
import com.finale.entity.LessonStudent;
import com.finale.entity.Location;
import com.finale.entity.Timetable;
import com.finale.exception.ResourceNotFoundException;
import com.finale.lesson.dto.ILessonCoachDTO;
import com.finale.lesson.dto.ILessonDTO;
import com.finale.lesson.dto.LessonDetailBasicDTO;
import com.finale.lesson.dto.LessonDetailResponseDTO;
import com.finale.lesson.dto.TimetableCreateDTO;
import com.finale.lesson.repository.LessonCoachRepository;
import com.finale.lesson.repository.LessonCustomRepository;
import com.finale.lesson.repository.LessonRepository;
import com.finale.lesson.repository.LessonStudentRepository;
import com.finale.lesson.repository.TimetableRepository;
import com.finale.lesson.dto.ILocationDTO;
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

    private final TimetableRepository timetableRepository;
    private final CoachRepository coachRepository;
    private final LessonRepository lessonRepository;
    private final LocationRepository locationRepository;
    private final LessonStudentRepository lessonStudentRepository;
    private final LessonCoachRepository lessonCoachRepository;
    private final LessonCustomRepository lessonCustomRepository;

    @Transactional
    public ApiResponse createTimetable(TimetableCreateDTO dto) throws ResourceNotFoundException {
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

    public ApiResponse getLessonDetails(Long id,String type) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 레슨을 찾을 수 없습니다."));

        if ("coach".equals(type)) {
            return ApiResponse.successResponse(new LessonDetailResponseDTO(lesson));
        } else {
            return ApiResponse.successResponse(new LessonDetailBasicDTO(lesson));
        }
    }

    public ApiResponse getAllLesson(String type) {
        List<Lesson> all = lessonRepository.findAll();
        if ("coach".equals(type)) {
            return ApiResponse.successResponse(all.stream().map(ILessonCoachDTO::new).toList());
        } else {
            return ApiResponse.successResponse(all.stream().map(ILessonDTO::new).toList());
        }
    }

    /**
     * 장소 별 레슨 조회
     * TODO : 월별 조회 조건이 들어가야 함
     * @param name
     * @param type
     * @return
     */
    public ApiResponse getLessonsByLocation(String name,String type) {
        Location location = locationRepository.findByName(name);

        if ("coach".equals(type)) {
            List<ILessonCoachDTO> lessonsByLocation = lessonCustomRepository.getLessonsByLocationForCoach(name);
            return ApiResponse.successResponse(new ILocationDTO<>(location, lessonsByLocation));
        } else {
            List<ILessonDTO> lessonsByLocation = lessonCustomRepository.getLessonsByLocation(name);
            return ApiResponse.successResponse(new ILocationDTO<>(location, lessonsByLocation));
        }
    }

    /**
     * 수강생 입금 확인
     * @param id LessonStudent.id 임
     * @return
     */
    @Transactional
    public ApiResponse updateDeposit(Long id) {
        LessonStudent findStudent = lessonStudentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 수강생을 찾을 수 없습니다."));
        findStudent.depositConfirm();

        return ApiResponse.successResponse("입금 확인이 완료 되었습니다.");
    }
}
