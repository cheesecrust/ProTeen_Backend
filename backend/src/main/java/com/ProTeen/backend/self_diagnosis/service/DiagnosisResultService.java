package com.ProTeen.backend.self_diagnosis.service;

import com.ProTeen.backend.self_diagnosis.dto.DiagnosisResultDTO;
import com.ProTeen.backend.self_diagnosis.entity.DiagnosisResult;

import java.util.List;

public interface DiagnosisResultService {
    //결과 입력
    void create(DiagnosisResult result);
    List<DiagnosisResult> searchResultByUser(String user);

    List<DiagnosisResultDTO> searchResultDTOByUser(String user);

    //결과 삭제
}
