package com.ProTeen.backend.self_diagnosis.repository;

import com.ProTeen.backend.self_diagnosis.domain.Diagnosis;
import com.ProTeen.backend.self_diagnosis.domain.Diagnosis_Name;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.context.transaction.TestTransaction.flagForCommit;


@SpringBootTest
@Transactional
class H2DiagnosisRepositoryTest {

    @Autowired
    private H2DiagnosisRepository diagnosisRepository;

    @Test
    @DisplayName("자가진단 저장, 카테고리 별로 하나 저장되는지 여부")
    void save_findByCategory(){
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setCategory(Diagnosis_Name.FIRST);
        diagnosisRepository.save(diagnosis);
        Diagnosis diagnosis1 = new Diagnosis();
        diagnosis1.setCategory(Diagnosis_Name.SECOND);
        diagnosisRepository.save(diagnosis1);

        Assertions.assertThat(diagnosisRepository.findByCategory(diagnosis.getCategory()).orElse(null))
                .isEqualTo(diagnosis);
    }
    @Test
    @DisplayName("자가진단 전체검색")
    void findAll() {
        int before = diagnosisRepository.findAll().size();
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setCategory(Diagnosis_Name.FIRST);
        diagnosisRepository.save(diagnosis);
        Diagnosis diagnosis1 = new Diagnosis();
        diagnosis1.setCategory(Diagnosis_Name.SECOND);
        diagnosisRepository.save(diagnosis1);
        Diagnosis diagnosis2 = new Diagnosis();
        diagnosis2.setCategory(Diagnosis_Name.SECOND);
        diagnosisRepository.save(diagnosis2);
        int after = diagnosisRepository.findAll().size();

        // 중복되면 하나만 남도록
        Assertions.assertThat(before + 2).isEqualTo(after);
    }

    @Test
    @DisplayName("각각의 질문이 잘 들어가는지")
    void addQuestion(){
        Diagnosis diagnosis1 = new Diagnosis();
        diagnosis1.getDignosis_list().add("첫번째");
        diagnosis1.getDignosis_list().add("두번째");
        diagnosis1.setCategory(Diagnosis_Name.FIRST);
        diagnosisRepository.save(diagnosis1);

        Diagnosis diagnosis2 = new Diagnosis();
        diagnosis2.getDignosis_list().add("첫번째");
        diagnosis2.getDignosis_list().add("두번째");
        diagnosis2.setCategory(Diagnosis_Name.SECOND);
        diagnosisRepository.save(diagnosis2);

        Assertions.assertThat(diagnosis1.getDignosis_list().size())
                .isEqualTo(diagnosis2.getDignosis_list().size());
    }
}