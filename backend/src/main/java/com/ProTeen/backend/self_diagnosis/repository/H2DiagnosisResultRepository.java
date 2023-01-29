package com.ProTeen.backend.self_diagnosis.repository;

import com.ProTeen.backend.self_diagnosis.domain.DiagnosisResult;
import com.ProTeen.backend.shelter.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class H2DiagnosisResultRepository implements DiagnosisResultRepository{

    @PersistenceContext
    private final EntityManager em;

    @Override
    public DiagnosisResult save(DiagnosisResult diagnosisResult) {
        em.persist(diagnosisResult);
        return diagnosisResult;
    }

    @Override
    public List<DiagnosisResult> findByMember(Member member) {
        return em.createQuery("select m from DiagnosisResult m where m.member = :member",DiagnosisResult.class)
                .setParameter("member",member)
                .getResultList();
    }
}

