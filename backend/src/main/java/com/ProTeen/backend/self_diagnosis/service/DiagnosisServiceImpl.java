package com.ProTeen.backend.self_diagnosis.service;

import com.ProTeen.backend.self_diagnosis.dto.DiagnosisDTO;
import com.ProTeen.backend.self_diagnosis.dto.DiagnosisDtoRepository;
import com.ProTeen.backend.self_diagnosis.entity.Diagnosis;
import com.ProTeen.backend.self_diagnosis.repository.JPADiagnosisRepository;
import com.ProTeen.backend.self_diagnosis.repository.JPADiagnosisResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class DiagnosisServiceImpl implements DiagnosisService{
    //검색
    private final JPADiagnosisRepository diagnosisRepository;
    private final JPADiagnosisResultRepository diagnosisResultRepository;
    private final DiagnosisDtoRepository diagnosisDtoRepository;
    public Optional<Diagnosis> searchQuestion(String id) {
        return diagnosisRepository.findById(id);
    }
    public List<Diagnosis> searchAll(){
        return diagnosisRepository.findAll();
    }
    public List<String> addDiagnosis_list(String category_name, String question){
        List<String> temp = diagnosisRepository.findById(category_name).get().getDignosis_list();
        temp.add(question);
        return temp;
    }
    public List<DiagnosisDTO> searchDTOAll(){
        return diagnosisDtoRepository.findAll();
    }
}
