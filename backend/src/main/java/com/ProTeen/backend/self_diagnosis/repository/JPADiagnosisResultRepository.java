package com.ProTeen.backend.self_diagnosis.repository;

import com.ProTeen.backend.self_diagnosis.entity.DiagnosisResult;
import com.ProTeen.backend.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JPADiagnosisResultRepository extends JpaRepository<DiagnosisResult,Long> {
    //구현체 필요 x
    List<DiagnosisResult> findByUser(UserEntity user);
}
