package com.ProTeen.backend.shelter.dto;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ShelterDtoRepository {

    private final EntityManager em;

    public List<ShelterDTO> findAll(){
        return em.createQuery("select new com.ProTeen.backend.shelter.dto.ShelterDTO(s.id,s.ctpvNm,s.fcltNm) " +
                        "from Shelter as s",ShelterDTO.class)
                .getResultList();
    }

    public List<ShelterDTO> findByCtpvNm(String ctpvNm){
        return em.createQuery("select new com.ProTeen.backend.shelter.dto.ShelterDTO(s.id,s.ctpvNm,s.fcltNm)" +
                        " from Shelter s where s.ctpvNm like concat('%',:ctpvNm,'%')",ShelterDTO.class)
                .setParameter("ctpvNm",ctpvNm)
                .getResultList();
    }

    public List<ShelterDTO> findByFcltNm(String fcltNm){
        return em.createQuery("select new com.ProTeen.backend.shelter.dto.ShelterDTO(s.id,s.ctpvNm,s.fcltNm)" +
                        " from Shelter s where s.fcltNm like concat('%',:fcltNm,'%') ",ShelterDTO.class)
                .setParameter("fcltNm",fcltNm)
                .getResultList();

    }
}
