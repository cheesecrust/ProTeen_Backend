package com.ProTeen.backend.shelter.dto;

import lombok.*;

@Data
public class ShelterDTO {
// 이름, 순번, 장소 이 세가지만 나오면 될거같은데?
    private Long id;
    private String ctpvNm;
    private String fcltNm;
    public ShelterDTO(Long id, String ctpvNm, String fcltNm) {
        this.id = id;
        this.ctpvNm = ctpvNm;
        this.fcltNm = fcltNm;
    }
}



