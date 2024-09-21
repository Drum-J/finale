package com.finale.coach.service;

import com.finale.coach.dto.CoachResponseDTO;
import com.finale.coach.dto.CreateNoticeDTO;
import com.finale.coach.dto.EnrollmentResponseDTO;
import com.finale.coach.dto.EnrollmentSearchDTO;
import com.finale.coach.dto.LessonChangeDTO;
import com.finale.coach.dto.LessonUpdateDTO;
import com.finale.coach.dto.S3UploadDTO;
import com.finale.coach.repository.CoachRepository;
import com.finale.common.ApiResponse;
import com.finale.entity.Coach;
import com.finale.entity.Lesson;
import com.finale.entity.LessonStudent;
import com.finale.entity.Location;
import com.finale.entity.Notice;
import com.finale.entity.Student;
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
import com.finale.sms.SmsService;
import com.finale.student.dto.StudentDTO;
import com.finale.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
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
    private final StudentRepository studentRepository;
    private final SmsService smsService;

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
        return ApiResponse.successResponse("코치 권한이 변경되었습니다. 현재 권한 : " + find.getCoachRole());
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
    public ApiResponse updateDeposit(Long id) throws CoolsmsException {
        log.info("=== 수강생 입금 확인 Service 진입 ===");
        log.info("=== LessonStudent Id = {} ===",id);

        LessonStudent findStudent = lessonStudentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 수강생을 찾을 수 없습니다."));
        findStudent.depositConfirm();

        Timetable timetable = findStudent.getLesson().getTimetable();
        Student student = findStudent.getStudent();

        smsService.depositSend(timetable, student);

        return ApiResponse.successResponse("입금 확인이 완료 되었습니다.");
    }

    @Transactional
    public ApiResponse createTimetable(TimetableCreateDTO dto) throws ResourceNotFoundException {
        log.info("=== 레슨 생성 Service 진입 ===");
        log.info("레슨 데이터 = {}",dto);

        Location findLocation = locationRepository.findByName(dto.getLocationName());
        if (findLocation == null) {
            throw new ResourceNotFoundException("해당 장소를 찾을 수 없습니다.");
        }

        Timetable timetable = dto.convertToEntity();

        Lesson lesson = new Lesson(timetable);

        timetableRepository.save(timetable);
        lessonRepository.save(lesson);

        /*List<Long> coachId = dto.getCoaches();
        coachId.stream().map(id -> coachRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("해당 코치를 찾을 수 없습니다.")))
                .map(coach -> {
                            LessonCoach lessonCoach = new LessonCoach(lesson, coach);
                            lessonCoachRepository.save(lessonCoach);

                            return lessonCoach;
                        }
                ).forEach(lesson::addCoaches);*/

        return ApiResponse.successResponse("레슨 생성을 완료했습니다.");
    }

    @Transactional
    public ApiResponse createNotice(CreateNoticeDTO dto) {
        Notice notice = dto.toEntity();

        noticeRepository.save(notice);

        return ApiResponse.successResponse("레슨 공지사항 생성을 완료했습니다.");
    }

    public ApiResponse getStudentList() {
        List<StudentDTO> list = studentRepository.findAll()
                .stream().map(StudentDTO::new).toList();

        return ApiResponse.successResponse(list);
    }

    public ApiResponse getEnrollmentList(EnrollmentSearchDTO dto) {
        List<EnrollmentResponseDTO> enrollmentList = lessonCustomRepository.getEnrollmentList(dto);
        return ApiResponse.successResponse(enrollmentList);
    }

    @Transactional
    public ApiResponse updateCoach(S3UploadDTO dto) {
        log.info("===== 단순 코치 이력 업데이트 =====");
        Long coachId = dto.id();

        Coach coach = coachRepository.findById(coachId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 코치를 찾을 수 없습니다."));

        coach.updateResume(dto.resume());

        return ApiResponse.successResponse("코치 이력이 변경 되었습니다.");
    }

    @Transactional
    public ApiResponse lessonCancel(Long id) {
        cancel(id);

        return ApiResponse.successResponse("수강취소를 완료 했습니다.");
    }

    @Transactional
    public ApiResponse lessonChange(LessonChangeDTO dto) {
        cancel(dto.lessonStudentId());

        Lesson lesson = lessonRepository.findById(dto.lessonId())
                .orElseThrow(() -> new ResourceNotFoundException("해당 레슨을 찾을 수 없습니다."));

        Student student = studentRepository.findById(dto.studentId())
                .orElseThrow(() -> new ResourceNotFoundException("해당 학생을 찾을 수 없습니다."));

        LessonStudent lessonStudent = new LessonStudent(lesson, student);

        lesson.addStudent(lessonStudent);

        return ApiResponse.successResponse("수강 변경을 완료 했습니다. 변경 레슨 = " + lesson.getTitle());
    }

    private void cancel(Long id) {
        LessonStudent lessonStudent = lessonStudentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 레슨을 찾을 수 없습니다."));

        lessonStudent.getLesson().enrolmentMinus();

        lessonStudentRepository.delete(lessonStudent);
    }

    @Transactional
    public ApiResponse updateLesson(LessonUpdateDTO dto) {

        Lesson lesson = lessonRepository.findById(dto.lessonId())
                .orElseThrow(() -> new ResourceNotFoundException("해당 레슨을 찾을 수 없습니다."));

        Location findLocation = locationRepository.findByName(dto.locationName());
        if (findLocation == null) {
            throw new ResourceNotFoundException("해당 장소를 찾을 수 없습니다.");
        }

        Timetable timetable = lesson.getTimetable();
        timetable.update(dto);

        /*List<Long> coaches = dto.coaches();*/

        //lessonCoach 엔티티 삭제
        lesson.getCoaches().clear();

        /*for (Long coach : coaches) {
            Coach findCoach = coachRepository.findById(coach)
                    .orElseThrow(() -> new ResourceNotFoundException("해당 코치를 찾을 수 없습니다."));

            LessonCoach lessonCoach = new LessonCoach(lesson, findCoach);
            lesson.addCoaches(lessonCoach);

            lessonCoachRepository.save(lessonCoach);
        }*/

        lesson.update(timetable);

        timetableRepository.save(timetable);
        lessonRepository.save(lesson);

        return ApiResponse.successResponse("레슨 변경을 완료했습니다.");
    }
}
