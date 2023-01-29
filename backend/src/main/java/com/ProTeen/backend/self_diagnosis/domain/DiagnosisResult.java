package com.ProTeen.backend.self_diagnosis.domain;

import com.ProTeen.backend.shelter.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class DiagnosisResult {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnosis_category")
    private Diagnosis diagnosis;

    private int score;

    private LocalDateTime diagnosisTime;

}

