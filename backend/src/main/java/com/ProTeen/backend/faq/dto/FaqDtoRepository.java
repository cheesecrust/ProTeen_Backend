package com.ProTeen.backend.faq.dto;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FaqDtoRepository {
    private final EntityManager em;

    public List<FaqDTO> findAll(){
        return em.createQuery("select new com.ProTeen.backend.faq.dto.FaqDTO(s.category) " +
                        "from Faq as s",FaqDTO.class)
                .getResultList();
    }
}
