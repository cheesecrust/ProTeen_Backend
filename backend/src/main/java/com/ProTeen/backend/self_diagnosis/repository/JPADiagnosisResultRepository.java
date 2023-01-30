package com.ProTeen.backend.self_diagnosis.repository;

import com.ProTeen.backend.self_diagnosis.domain.DiagnosisResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPADiagnosisResultRepository extends JpaRepository<DiagnosisResult,Long> {
    //구현체 필요 x
}
