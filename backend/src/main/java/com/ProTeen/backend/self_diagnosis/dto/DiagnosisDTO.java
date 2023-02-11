package com.ProTeen.backend.self_diagnosis.dto;

import lombok.Data;

@Data
public class DiagnosisDTO {
    private String category_name;

    public DiagnosisDTO(String category_name) {
        this.category_name = category_name;
    }
}
