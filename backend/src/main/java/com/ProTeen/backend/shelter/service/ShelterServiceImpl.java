package com.ProTeen.backend.shelter.service;

import com.ProTeen.backend.shelter.entity.Shelter;
import com.ProTeen.backend.shelter.dto.ShelterDTO;
import com.ProTeen.backend.shelter.dto.ShelterDtoRepository;
import com.ProTeen.backend.shelter.repository.JPAFeedbackRepository;
import com.ProTeen.backend.shelter.repository.JPAShelterRepository;
import com.ProTeen.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ShelterServiceImpl implements ShelterService {

    private final JPAShelterRepository shelterRepository;
    private final UserService userService;
    private final JPAFeedbackRepository feedbackRepository;
    private final ShelterDtoRepository shelterDtoRepository;


    // ORIGINAL VERSION
    public List<Shelter> readByCtpvNm(String ctpvNm){
        return shelterRepository.findByCtpvNmContaining(ctpvNm);
    }
    public List<Shelter> readByFcltNm(String fcltNm){
        return shelterRepository.findByFcltNmContaining(fcltNm);
    }
    public List<Shelter> readAll(){
        return shelterRepository.findAll();
    }

    // DTO VERSION
    public List<ShelterDTO> readDtoAll(){
        return shelterDtoRepository.findAll();
    }
    public List<ShelterDTO> readDtoByCtpvNm(String ctpvNm){
        return shelterDtoRepository.findByCtpvNm(ctpvNm);
    }
    public List<ShelterDTO> readDtoByFcltNm(String fcltNm){
        return shelterDtoRepository.findByFcltNm(fcltNm);
    }

    public List<Shelter> readSheletrDetail(Long id){
        return shelterRepository.findById(id).stream().toList();
    }

    // 생성(비즈니스 로직)


}
