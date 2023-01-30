package com.ProTeen.backend.shelter.dbconnection;

import com.ProTeen.backend.shelter.domain.Feedback;
import com.ProTeen.backend.shelter.domain.Member;
import com.ProTeen.backend.shelter.domain.Score;
import com.ProTeen.backend.shelter.domain.Shelter;
import com.ProTeen.backend.shelter.repository.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import static org.springframework.test.context.transaction.TestTransaction.flagForCommit;

@SpringBootTest
@Transactional
class FeedbackTest {
    @Autowired
    private JPAFeedbackRepository feedbackRepository;
    @Autowired
    private JPAShelterRepository shelterRepository;
    @Autowired
    private JPAMemberRepository memberRepository;

    @Test
    @DisplayName("피드백이_들어가나요?")
    void 피드백이_들어가나요() {

        //쉼터 저장
        Shelter shelter1 = new Shelter();
        shelter1.setCtpvNm("shelter_name");
        shelterRepository.save(shelter1);

        // 멤버 저장
        Member member1 = new Member();
        memberRepository.save(member1);

        Feedback feedback1 = new Feedback();
        feedback1.setMember(member1);
        feedback1.setShelter(shelter1);
        feedback1.setScore(Score.GOOD);
        feedback1.setComment("hello");
        feedbackRepository.save(feedback1);


        // 저장한 멤버 아이디로 검색
        Optional<Feedback> feedback = feedbackRepository.findAll().stream().findAny();
        System.out.println(feedback.get().getComment());
        System.out.println(feedback.get().getScore());
        System.out.println(feedback.get().getShelter().getCtpvNm());
        Assertions.assertThat(feedback1.getId()).isEqualTo(feedback.get().getId()
        );

    }

}