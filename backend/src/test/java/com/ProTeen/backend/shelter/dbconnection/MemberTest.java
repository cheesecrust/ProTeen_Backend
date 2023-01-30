package com.ProTeen.backend.shelter.dbconnection;

import com.ProTeen.backend.shelter.domain.Member;
import com.ProTeen.backend.shelter.repository.H2MemberRepository;
import com.ProTeen.backend.shelter.repository.JPAMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.context.transaction.TestTransaction.flagForCommit;

@SpringBootTest
@Transactional
class MemberTest {
    @Autowired
    private JPAMemberRepository memberRepository;

    @Test
    @DisplayName("회원가입")
    void 회원가입() {
        flagForCommit();
        // 멤버 저장
        Member member1 = new Member();
        member1.setName("minseok2");
        memberRepository.save(member1);

        // 저장한 멤버 아이디로 검색
        Member findMember = memberRepository.findById(member1.getId()).get();
        System.out.println("findMemberName = " + findMember.getName());
        System.out.println("findMemberId = " + findMember.getId());
        Assertions.assertThat(member1.getName()).isEqualTo(findMember.getName());
    }

}


