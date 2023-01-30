package com.ProTeen.backend.shelter.repository;

import com.ProTeen.backend.shelter.domain.Shelter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Repository
@RequiredArgsConstructor
public class H2ShelterRepository implements ShelterRepository{

    private final EntityManager em;

    @Override
    public Shelter save(Shelter shelter) {
        if(shelter.getId() == null){
            em.persist(shelter);
        }
        else{
            em.merge(shelter);
        }
        return shelter;
    }

    @Override
    public Optional<Shelter> findById(Long id) {
        Shelter shelter = em.find(Shelter.class,id);
        return Optional.ofNullable(shelter);
    }

    @Override
    public List<Shelter> findAllShelterPagination(int start, int number) {
        return em.createQuery("select m from Shelter m", Shelter.class)
                .setFirstResult(start)
                .setMaxResults(number)
                .getResultList();
    }

    @Override
    public List<Shelter> findByCtpvNm(String ctpvNm) {
        return em.createQuery("select m from Shelter m where m.ctpvNm =:ctpvNm", Shelter.class)
                .setParameter("ctpvNm",ctpvNm)
                .getResultList();
    }

    @Override
    public List<Shelter> findByCtpvNmPagination(String ctpvNm,int start, int number) {
        return em.createQuery("select m from Shelter m where m.ctpvNm =:ctpvNm", Shelter.class)
                .setParameter("ctpvNm",ctpvNm)
                .setFirstResult(start)
                .setMaxResults(number)
                .getResultList();
    }


}
