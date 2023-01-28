package com.ProTeen.backend.shelter.dbconnection;

import com.ProTeen.backend.shelter.domain.Shelter;
import com.ProTeen.backend.shelter.repository.H2ShelterRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.context.transaction.TestTransaction.flagForCommit;

@SpringBootTest
@Transactional
class ShelterTest {

    @Autowired
    private H2ShelterRepository shelterRepository;

    @Test
    @DisplayName("쉘터가 들어가나요")
    void 쉘터가_저장되나요(){
        flagForCommit();
        Shelter shelter1 = new Shelter();
        shelter1.setCpctCnt(100);
        shelter1.setCtpvNm("hello");
        shelterRepository.save(shelter1);

        Shelter shelter = shelterRepository.findById(shelter1.getId()).get();
        System.out.println(shelter.getCtpvNm());
        Assertions.assertThat(shelter1.getId()).isEqualTo(shelter.getId());
    }
}