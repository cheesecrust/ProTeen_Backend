package com.ProTeen.backend.self_diagnosis.repository;

import com.ProTeen.backend.self_diagnosis.domain.Diagnosis;
import com.ProTeen.backend.self_diagnosis.domain.Diagnosis_Name;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Repository
@RequiredArgsConstructor
public class H2DiagnosisRepository implements DiagnosisRepository{


    private final EntityManager em;

    @Override
    public Diagnosis save(Diagnosis diagnosis) {
        Diagnosis temp = em.find(Diagnosis.class,diagnosis.getId());
        if(temp == null)
            em.persist(diagnosis);
        else
            em.merge(diagnosis);
        em.flush();
        return diagnosis;
    }

    @Override
    public Optional<Diagnosis> findById(Diagnosis_Name category) {
        List<Diagnosis> result = em.createQuery("select m from Diagnosis m where m.id = :category",Diagnosis.class)
                .setParameter("category",category)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Diagnosis> findAll() {
        return em.createQuery("select m from Diagnosis m",Diagnosis.class)
                .getResultList();
    }
}
