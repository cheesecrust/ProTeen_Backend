package com.ProTeen.backend.self_diagnosis.entity;

import jakarta.persistence.*;
import lombok.*;


import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Diagnosis {
    @Id
    @NonNull
    private String category_name;


    @ElementCollection
    @CollectionTable(name = "diagnosis_questions",joinColumns = @JoinColumn(name= "category_name"))
    @Column(name = "Questions")
    private List<String> dignosis_list = new ArrayList<>();

}
