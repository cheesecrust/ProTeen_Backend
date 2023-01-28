package com.ProTeen.backend.shelter.repository;

import com.ProTeen.backend.shelter.domain.Feedback;
import com.ProTeen.backend.shelter.domain.Member;
import com.ProTeen.backend.shelter.domain.Shelter;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;


@Transactional
@SpringBootTest
class H2FeedbackRepositoryTest {

    @Autowired
    private H2FeedbackRepository feedbackRepository;
    @Autowired
    private H2ShelterRepository shelterRepository;
    @Autowired
    private H2MemberRepository memberRepository;



    @Test
    @DisplayName("피드백 저장과 ID검색")
    void save_findById() {
        Feedback testFeedback = new Feedback();
        feedbackRepository.save(testFeedback);
        Assertions.assertThat(feedbackRepository.findById(testFeedback.getId()).get())
                .isEqualTo(testFeedback);
    }

    @Test
    @DisplayName("피드백 전체 검색")
    void findAll() {
        int before = feedbackRepository.findAll().size();
        Feedback testFeedback1 = new Feedback();
        feedbackRepository.save(testFeedback1);
        Feedback testFeedback2 = new Feedback();
        feedbackRepository.save(testFeedback2);
        int after = feedbackRepository.findAll().size();
        Assertions.assertThat(before + 2).isEqualTo(after);
    }

    @Test
    @DisplayName("댓글이 어느 쉼터에 달렸는지 검색")
    void findByShelter() {

        Shelter testShelter = new Shelter();
        shelterRepository.save(testShelter);
        Member testmember = new Member();
        memberRepository.save(testmember);

        Feedback testFeedback = new Feedback();
        testFeedback.setShelter(testShelter);
        testFeedback.setMember(testmember);
        feedbackRepository.save(testFeedback);

        List<Feedback> temp = feedbackRepository.findByShelter(testShelter);
        System.out.println(temp);

        Assertions.assertThat(testFeedback)
                .isEqualTo(feedbackRepository.findByShelter(testFeedback.getShelter()).stream().findAny().orElse(null));
    }
}
