package com.ProTeen.backend.self_diagnosis.dto;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DiagnosisDtoRepository {
    private final EntityManager em;

    public List<DiagnosisDTO> findAll(){
        return em.createQuery("select new com.ProTeen.backend.self_diagnosis.dto.DiagnosisDTO(s.category_name) " +
                        "from Diagnosis as s",DiagnosisDTO.class)
                .getResultList();
    }
}
