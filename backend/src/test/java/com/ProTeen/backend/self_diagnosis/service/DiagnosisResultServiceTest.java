package com.ProTeen.backend.self_diagnosis.service;

import com.ProTeen.backend.self_diagnosis.repository.JPADiagnosisRepository;
import com.ProTeen.backend.self_diagnosis.repository.JPADiagnosisResultRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class DiagnosisResultServiceTest {

    @Autowired
    private DiagnosisResultService diagnosisResultService;
    @Autowired
    private JPADiagnosisResultRepository diagnosisResultRepository;

}