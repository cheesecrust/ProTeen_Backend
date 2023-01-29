package com.ProTeen.backend.self_diagnosis.repository;

import com.ProTeen.backend.self_diagnosis.domain.Diagnosis;
import com.ProTeen.backend.self_diagnosis.domain.Diagnosis_Name;

import java.util.List;
import java.util.Optional;

public interface DiagnosisRepository {

    Diagnosis save(Diagnosis diagnosis);
    Optional<Diagnosis> findByCategory(Diagnosis_Name diagnosisName);
    List<Diagnosis> findAll();
}
