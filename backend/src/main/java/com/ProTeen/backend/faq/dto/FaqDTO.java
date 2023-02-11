package com.ProTeen.backend.faq.dto;

import lombok.Data;

@Data
public class FaqDTO {
    private String category;

    public FaqDTO(String category) {
        this.category = category;
    }
}
