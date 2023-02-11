package com.ProTeen.backend.self_diagnosis.entity;

import com.ProTeen.backend.user.model.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiagnosisResult {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnosis_category")
    private Diagnosis diagnosis;

    private int score;
    private String diagnosisName;

    private LocalDateTime diagnosisTime;

}

