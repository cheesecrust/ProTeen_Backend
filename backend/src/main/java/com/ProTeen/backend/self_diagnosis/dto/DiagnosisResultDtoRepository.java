package com.ProTeen.backend.self_diagnosis.dto;

import com.ProTeen.backend.user.model.UserEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DiagnosisResultDtoRepository {
    private final EntityManager em;

    public List<DiagnosisResultDTO> findByUser(UserEntity user){
        return em.createQuery("select new com.ProTeen.backend.self_diagnosis.dto.DiagnosisResultDTO(s.score,s.diagnosisName) " +
                        "from DiagnosisResult as s "+
                        "where s.user =: user", DiagnosisResultDTO.class)
                .setParameter("user",user)
                .getResultList();
    }
}
