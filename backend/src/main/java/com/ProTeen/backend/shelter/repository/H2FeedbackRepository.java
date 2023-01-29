package com.ProTeen.backend.shelter.repository;

import com.ProTeen.backend.shelter.domain.Feedback;
import com.ProTeen.backend.shelter.domain.Shelter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class H2FeedbackRepository implements FeedbackRepository{

    @PersistenceContext
    private final EntityManager em;
    @Override
    public Feedback save(Feedback feedback) {
        em.persist(feedback);
        return feedback;
    }

    @Override
    public List<Feedback> findAll() {
        List<Feedback> resultList = em.createQuery("select m from Feedback m",Feedback.class).getResultList();
        return resultList;
    }


    @Override
    public List<Feedback> findByShelter(Shelter shelter) {
        return em.createQuery("select m from Feedback m left join fetch m.shelter where m.shelter = :shelter",Feedback.class)
                .setParameter("shelter",shelter)
                .getResultList();
    }

    @Override
    public Optional<Feedback> findById(long id) {
        Feedback result = em.find(Feedback.class,id);
        return Optional.ofNullable(result);
    }
}
