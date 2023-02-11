package com.ProTeen.backend.faq.service;

import com.ProTeen.backend.faq.dto.FaqDtoRepository;
import com.ProTeen.backend.faq.entity.Faq;
import com.ProTeen.backend.faq.repository.JPAFaqRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class FaqServiceTest {

    @Autowired
    private FaqService faqService;
    @Autowired
    private FaqDtoRepository faqDtoRepository;
    @Autowired
    private JPAFaqRepository faqRepository;

    @Test
    @DisplayName("Faq 저장")
    void SaveFaq(){
        String category = "테스트 카테고리 1";
        String info1 = "테스트 카테고리 1번의 1번";

        Faq temp = new Faq();
        temp.getInfo_list().add(info1);
        temp.setCategory(category);
        faqRepository.save(temp);

        String info2 = "테스트 카테고리 1번의 2번";
        faqService.addNewFaq(category,info2);

        Assertions.assertThat(faqRepository.findById(category).get().getInfo_list().size()).isEqualTo(2);
    }
}