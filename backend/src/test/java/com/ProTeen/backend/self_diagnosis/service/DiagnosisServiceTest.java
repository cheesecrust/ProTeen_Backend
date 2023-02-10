package com.ProTeen.backend.self_diagnosis.service;

import com.ProTeen.backend.self_diagnosis.entity.Diagnosis;
import com.ProTeen.backend.self_diagnosis.repository.JPADiagnosisRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class DiagnosisServiceTest {

    @Autowired
    private DiagnosisService diagnosisService;
    @Autowired
    private JPADiagnosisRepository diagnosisRepository;

    @Test
    @DisplayName("자가진단 문항 저장")
    void DiagnosisQuestionSave(){
        String question = "1번 테스트의 문항 1";
        String diagnosis = "1번 테스트";

        Diagnosis temp = new Diagnosis();
        temp.getDignosis_list().add(question);
        temp.setCategory_name(diagnosis);
        diagnosisRepository.save(temp);

        diagnosisService.addDiagnosis_list(diagnosis,question);

        Assertions.assertThat(diagnosisRepository.findById(diagnosis).get().getDignosis_list().size()).isEqualTo(2);
    }
}