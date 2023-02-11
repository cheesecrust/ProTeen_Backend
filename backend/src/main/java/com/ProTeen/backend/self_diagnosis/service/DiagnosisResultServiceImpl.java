package com.ProTeen.backend.self_diagnosis.service;

import com.ProTeen.backend.self_diagnosis.dto.DiagnosisResultDTO;
import com.ProTeen.backend.self_diagnosis.dto.DiagnosisResultDtoRepository;
import com.ProTeen.backend.self_diagnosis.entity.DiagnosisResult;
import com.ProTeen.backend.self_diagnosis.repository.JPADiagnosisResultRepository;
import com.ProTeen.backend.user.model.UserEntity;
import com.ProTeen.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiagnosisResultServiceImpl implements DiagnosisResultService{

    private final UserRepository userRepository;
    private final JPADiagnosisResultRepository diagnosisResultRepository;
    private final DiagnosisResultDtoRepository diagnosisResultDtoRepository;

    public void create(DiagnosisResult result) {
        diagnosisResultRepository.save(result);
    }

    // 나중에 마이페이지에서 사용(Service 단독)
    public List<DiagnosisResult> searchResultByUser(String userId) {
        UserEntity user = userRepository.findById(userId).get();
        return diagnosisResultRepository.findByUser(user);
    }
    public List<DiagnosisResultDTO> searchResultDTOByUser(String userId) {
        UserEntity user = userRepository.findById(userId).get();
        return diagnosisResultDtoRepository.findByUser(user);
    }
}
