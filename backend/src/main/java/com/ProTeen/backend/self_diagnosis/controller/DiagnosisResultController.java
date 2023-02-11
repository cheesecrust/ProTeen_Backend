package com.ProTeen.backend.self_diagnosis.controller;

import com.ProTeen.backend.self_diagnosis.dto.DiagnosisResultDTO;
import com.ProTeen.backend.self_diagnosis.entity.DiagnosisResult;
import com.ProTeen.backend.self_diagnosis.repository.JPADiagnosisRepository;
import com.ProTeen.backend.self_diagnosis.repository.JPADiagnosisResultRepository;
import com.ProTeen.backend.self_diagnosis.service.DiagnosisResultService;
import com.ProTeen.backend.user.dto.ResponseDTO;
import com.ProTeen.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("diagnosisresult")
public class DiagnosisResultController {

    private final DiagnosisResultService diagnosisResultService;
    private final UserRepository userRepository;
    private final JPADiagnosisResultRepository diagnosisResultRepository;
    private final JPADiagnosisRepository diagnosisRepository;

    @PostMapping("/result/{diagnosisName}")
    public ResponseEntity<?> create(@AuthenticationPrincipal String userId, @RequestBody Map<String,Integer> scoreMap, @PathVariable String diagnosisName){
        try {
            int score = scoreMap.get("score");
            DiagnosisResult diagnosisResult = DiagnosisResult.builder()
                    .score(score)
                    .diagnosisTime(LocalDateTime.now())
                    .user(userRepository.findById(userId).get())
                    .diagnosis(diagnosisRepository.findById(diagnosisName).get())
                    .diagnosisName(diagnosisRepository.findById(diagnosisName).get().getCategory_name())
                    .build();
            diagnosisResultService.create(diagnosisResult);
            return ResponseEntity.ok().body("created");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 비즈니스 로직
//    @GetMapping("/user")
//    public ResponseEntity<?> searchById(@AuthenticationPrincipal String userId){
//        List<DiagnosisResult> diagnosisResultList = diagnosisResultService.searchResultByUser(userId);
//        ResponseDTO<DiagnosisResult> responseDTO = ResponseDTO.<DiagnosisResult>builder().data(diagnosisResultList).build();
//        return ResponseEntity.ok().body(responseDTO);
//    }
    @GetMapping("/user")
    public ResponseEntity<?> searchDTOById(@AuthenticationPrincipal String userId){
        try {
            List<DiagnosisResultDTO> diagnosisResultDTOList = diagnosisResultService.searchResultDTOByUser(userId);
            ResponseDTO<DiagnosisResultDTO> responseDTO = ResponseDTO.<DiagnosisResultDTO>builder().data(diagnosisResultDTOList).build();
            return ResponseEntity.ok().body(responseDTO);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
