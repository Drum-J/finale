package com.finale.coach.service;

import com.finale.coach.dto.CoachResponseDTO;
import com.finale.coach.repository.CoachRepository;
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

    public List<CoachResponseDTO> getCoachList() {
        return coachRepository.findAll().stream().map(
                coach -> new CoachResponseDTO(coach.getId(), coach.getName())).toList();
    }
}
