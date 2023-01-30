package com.ProTeen.backend.shelter.repository;

import com.ProTeen.backend.shelter.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JPAMemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByName(String name);
}
