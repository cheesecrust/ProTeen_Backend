package com.ProTeen.backend.self_diagnosis.repository;

import com.ProTeen.backend.self_diagnosis.entity.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPADiagnosisRepository extends JpaRepository<Diagnosis,String> {

    // save 를 구현해야합니다....
}
