package com.ProTeen.backend.self_diagnosis.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.context.annotation.Primary;


import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Diagnosis {
    @Id
    @Column(name= "diagnosis_category")
    @Enumerated(EnumType.STRING)
    @NonNull
    private Diagnosis_Name id;


    @ElementCollection
    @CollectionTable(name = "diagnosis_questions",joinColumns = @JoinColumn(name= "diagnosis_id"))
    @Column(name = "Questions")
    private List<String> dignosis_list = new ArrayList<>();
}
