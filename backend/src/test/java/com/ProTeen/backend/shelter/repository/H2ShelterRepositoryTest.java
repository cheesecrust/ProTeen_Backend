package com.ProTeen.backend.shelter.repository;

import com.ProTeen.backend.shelter.domain.Shelter;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Transactional
@SpringBootTest
class H2ShelterRepositoryTest {
    @Autowired
    private JPAShelterRepository shelterRepository;

    @Test
    @DisplayName("쉼터 전체 검색")
    void save_findById() {
        Shelter shelter = new Shelter();
        shelterRepository.save(shelter);
        Assertions.assertThat(shelter).isEqualTo(shelterRepository.findById(shelter.getId()).orElse(null));
    }

//    @Test
//    @DisplayName("쉼터 전체 검색, 페이지네이션")
//    void findAllShelterPagination() {
//        Shelter shelter1 = new Shelter();
//        shelterRepository.save(shelter1);
//        Shelter shelter2 = new Shelter();
//        shelterRepository.save(shelter2);
//        Shelter shelter3 = new Shelter();
//        shelterRepository.save(shelter3);
//        Assertions.assertThat(shelterRepository.findAllShelterPagination(0,2).size())
//                .isEqualTo(2);
//    }

    @Test
    @DisplayName("시,도에 따라 검색")
    void findByCtpvNm() {
        Shelter shelter1 = new Shelter();
        shelter1.setCtpvNm("first");
        shelterRepository.save(shelter1);
        Shelter shelter2 = new Shelter();
        shelter2.setCtpvNm("second");
        shelterRepository.save(shelter2);
        Shelter shelter3 = new Shelter();
        shelter3.setCtpvNm("second");
        shelterRepository.save(shelter3);
        Assertions.assertThat(shelterRepository.findByCtpvNm(shelter2.getCtpvNm()).size()).isEqualTo(2);
        Assertions.assertThat(shelterRepository.findByCtpvNm(shelter1.getCtpvNm()).size()).isEqualTo(1);
    }

//    @Test
//    @DisplayName("시,도에 따라 검색, 페이지네이션")
//    void findByCtpvNmPagination() {
//        Shelter shelter1 = new Shelter();
//        shelter1.setCtpvNm("first");
//        shelterRepository.save(shelter1);
//        Shelter shelter2 = new Shelter();
//        shelter2.setCtpvNm("second");
//        shelterRepository.save(shelter2);
//        Shelter shelter3 = new Shelter();
//        shelter3.setCtpvNm("second");
//        shelterRepository.save(shelter3);
//        Shelter shelter4 = new Shelter();
//        shelter4.setCtpvNm("second");
//        shelterRepository.save(shelter4);
//        Assertions.assertThat(shelterRepository.findByCtpvNmPagination(shelter2.getCtpvNm(),0,2).size()).isEqualTo(2);
//        Assertions.assertThat(shelterRepository.findByCtpvNmPagination(shelter1.getCtpvNm(),0,2).size()).isEqualTo(1);
//
//    }
}