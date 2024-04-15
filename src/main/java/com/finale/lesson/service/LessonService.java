package com.finale.lesson.service;

import com.finale.entity.*;
import com.finale.coach.repository.CoachRepository;
import com.finale.lesson.dto.*;
import com.finale.lesson.repository.LessonRepository;
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
public class LessonService {

    private final TimetableRepository timetableRepository;
    private final CoachRepository coachRepository;
    private final LessonRepository lessonRepository;
    private final LocationRepository locationRepository;

    @Transactional
    public void createTimetable(TimetableCreateDTO dto) {
        Location findLocation = locationRepository.findByName(dto.getLocation());
        if (findLocation == null) {
            throw new IllegalArgumentException("해당 장소를 찾을 수 없습니다.");
        }

        Timetable timetable = dto.convertToEntity();

        Lesson lesson = new Lesson(timetable);

        List<Long> coachId = dto.getCoachId();
        coachId.stream().map(id -> coachRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 코치를 찾을 수 없습니다.")))
                .map(coach -> new LessonCoach(lesson,coach)).forEach(lesson::addCoaches);

        timetableRepository.save(timetable);
        lessonRepository.save(lesson);
    }

    public LessonDetailResponseDTO getLessonDetails(Long id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 레슨을 찾을 수 없습니다."));

        return new LessonDetailResponseDTO(lesson);
    }

    public List<LessonResponseDTO> getAllLesson() {
        List<Lesson> all = lessonRepository.findAll();

        return all.stream().map(LessonResponseDTO::new).toList();
    }

    public LessonDetailBasicDTO getLessonStudentDetails(Long id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 레슨을 찾을 수 없습니다."));

        return new LessonDetailBasicDTO(lesson);
    }

    public List<LessonBasicDTO> getAllLessonStudents() {
        List<Lesson> all = lessonRepository.findAll();

        return all.stream().map(LessonBasicDTO::new).toList();
    }
}
