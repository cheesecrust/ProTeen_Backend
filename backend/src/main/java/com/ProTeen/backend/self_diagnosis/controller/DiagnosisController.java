package com.ProTeen.backend.self_diagnosis.controller;

import com.ProTeen.backend.self_diagnosis.dto.DiagnosisDTO;
import com.ProTeen.backend.self_diagnosis.entity.Diagnosis;
import com.ProTeen.backend.self_diagnosis.repository.JPADiagnosisRepository;
import com.ProTeen.backend.self_diagnosis.service.DiagnosisServiceImpl;
import com.ProTeen.backend.user.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("diagnosis")
public class DiagnosisController {
    private final DiagnosisServiceImpl diagnosisService;
    private final JPADiagnosisRepository diagnosisRepository;

    @GetMapping("/")
    public ResponseEntity<?> searchAll(){
        try{
            ResponseDTO<Diagnosis> responseDTO = ResponseDTO.<Diagnosis>builder().data(diagnosisService.searchAll()).build();
            return ResponseEntity.ok().body(responseDTO);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/list")
    public ResponseEntity<?> searchAllDTO(){
        try {
            ResponseDTO<DiagnosisDTO> responseDTO = ResponseDTO.<DiagnosisDTO>builder().data(diagnosisService.searchDTOAll()).build();
            return ResponseEntity.ok().body(responseDTO);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{diagnosis}")
    public ResponseEntity<?> searchDiagnosisQuestion(@PathVariable String diagnosis){
        try {
            Diagnosis questions = diagnosisService.searchQuestion(diagnosis).get();
            return ResponseEntity.ok().body(questions);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // 비즈니스 로직
    @PostMapping("/question/{diagnosis}")
    public ResponseEntity<?> createNewQuestion(@RequestBody Map<String,String> questionMap, @PathVariable String diagnosis){
        String question = questionMap.get("question");
        try {
            if (diagnosisRepository.findById(diagnosis).isPresent()) {
                Diagnosis temp = diagnosisRepository.findById(diagnosis).get();
                temp.setDignosis_list(diagnosisService.addDiagnosis_list(diagnosis,question));
                diagnosisRepository.save(temp);
            }
            else {
                Diagnosis temp = new Diagnosis();
                temp.getDignosis_list().add(question);
                temp.setCategory_name(diagnosis);
                diagnosisRepository.save(temp);
            }
            return ResponseEntity.ok().body("created");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
