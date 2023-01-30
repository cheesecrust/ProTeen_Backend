package com.ProTeen.backend.self_diagnosis.repository;

import com.ProTeen.backend.self_diagnosis.domain.Diagnosis;
import com.ProTeen.backend.self_diagnosis.domain.Diagnosis_Name;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JPADiagnosisRepository extends JpaRepository<Diagnosis,Diagnosis_Name> {

    // save 를 구현해야합니다....
}
