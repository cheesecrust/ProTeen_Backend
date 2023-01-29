package com.ProTeen.backend.self_diagnosis.repository;

import com.ProTeen.backend.self_diagnosis.domain.Diagnosis;
import com.ProTeen.backend.self_diagnosis.domain.DiagnosisResult;
import com.ProTeen.backend.self_diagnosis.domain.Diagnosis_Name;
import com.ProTeen.backend.shelter.domain.Member;
import com.ProTeen.backend.shelter.repository.H2MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@SpringBootTest
@Transactional
class H2DiagnosisResultRepositoryTest {

    @Autowired
    private H2DiagnosisResultRepository diagnosisResultRepository;
    @Autowired
    private H2DiagnosisRepository diagnosisRepository;
    @Autowired
    private H2MemberRepository memberRepository;

    @Test
    @DisplayName("테스트 결과 저장, 멤버 별로 구분이 가능여부")
    void save_findByMember(){
        Diagnosis diagnosis1 = new Diagnosis();
        diagnosis1.setCategory(Diagnosis_Name.FIRST);
        diagnosisRepository.save(diagnosis1);

        Member member = new Member();
        memberRepository.save(member);

        DiagnosisResult diagnosisResult = new DiagnosisResult();
        diagnosisResult.setDiagnosis(diagnosis1);
        diagnosisResult.setMember(member);
        diagnosisResult.setDiagnosis(diagnosis1);
        diagnosisResult.setDiagnosisTime(LocalDateTime.now());
        diagnosisResultRepository.save(diagnosisResult);

        System.out.println("LocalDateTime = " + diagnosisResult.getDiagnosisTime());

        Assertions.assertThat(diagnosisResult.getDiagnosis()).isEqualTo(diagnosis1);
        Assertions.assertThat(diagnosisResult.getMember()).isEqualTo(member);
    }
}