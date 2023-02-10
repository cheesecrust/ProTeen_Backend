package com.ProTeen.backend.self_diagnosis.service;

import com.ProTeen.backend.self_diagnosis.dto.DiagnosisDTO;
import com.ProTeen.backend.self_diagnosis.entity.Diagnosis;

import java.util.List;
import java.util.Optional;

public interface DiagnosisService {
    //검색
    Optional<Diagnosis> searchQuestion(String name);
    List<Diagnosis> searchAll();
    List<String> addDiagnosis_list(String category_name, String question);
    List<DiagnosisDTO> searchDTOAll();
}
