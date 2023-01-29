package com.ProTeen.backend.self_diagnosis.repository;

import com.ProTeen.backend.self_diagnosis.domain.DiagnosisResult;
import com.ProTeen.backend.shelter.domain.Member;

import java.util.List;

public interface DiagnosisResultRepository {

    DiagnosisResult save(DiagnosisResult diagnosisResult);
    List<DiagnosisResult> findByMember(Member member);

}
