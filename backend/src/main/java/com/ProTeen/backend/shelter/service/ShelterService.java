package com.ProTeen.backend.shelter.service;

import com.ProTeen.backend.shelter.entity.Shelter;
import com.ProTeen.backend.shelter.dto.ShelterDTO;

import java.util.List;

public interface ShelterService {
    List<Shelter> readByCtpvNm(String ctpvNm);
    List<Shelter> readByFcltNm(String fcltNm);
    List<Shelter> readAll();
    List<ShelterDTO> readDtoAll();
    List<ShelterDTO> readDtoByCtpvNm(String ctpvNm);
    List<ShelterDTO> readDtoByFcltNm(String fcltNm);
    List<Shelter> readSheletrDetail(Long id);
}
