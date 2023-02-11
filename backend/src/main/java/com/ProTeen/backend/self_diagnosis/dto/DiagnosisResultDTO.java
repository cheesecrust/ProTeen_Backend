package com.ProTeen.backend.self_diagnosis.dto;

import lombok.Data;

@Data

public class DiagnosisResultDTO {
    private int score;
    private String diagnosis;
    public DiagnosisResultDTO(int score,String diagnosis) {
        this.score = score;
        this.diagnosis = diagnosis;
    }
}
