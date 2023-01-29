package com.ProTeen.backend.shelter.repository;

import com.ProTeen.backend.shelter.domain.Member;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@Transactional
@SpringBootTest
class H2MemberRepositoryTest {

    @Autowired
    private H2MemberRepository memberRepository;
    @Test
    @DisplayName("멤버 저장과 Id 검색")
    void save_findById() {
        Member member = new Member();
        memberRepository.save(member);
        Assertions.assertThat(member)
                .isEqualTo(memberRepository.findById(member.getId())
                        .orElse(null));
    }

    @Test
    @DisplayName("멤버 이름으로 검색")
    void findByName() {
        Member member = new Member();
        member.setName("Hello");
        memberRepository.save(member);
        Assertions.assertThat(member)
                .isEqualTo(memberRepository.findByName(member.getName())
                        .orElse(null));
    }

    @Test
    @DisplayName("멤버 전체 검색")
    void findAll() {
        int before = memberRepository.findAll().size();
        Member member1 = new Member();
        memberRepository.save(member1);
        Member member2 = new Member();
        memberRepository.save(member2);
        int after = memberRepository.findAll().size();
        Assertions.assertThat(before + 2).isEqualTo(after);
    }
}