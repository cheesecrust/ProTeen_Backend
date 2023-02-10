package com.ProTeen.backend.faq.entity;

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
public class Faq {

    @Id
    @NonNull
    public String category;
    @ElementCollection
    @CollectionTable(name = "info_list",joinColumns = @JoinColumn(name= "category_name"))
    @Column(name = "information")
    private List<String> info_list = new ArrayList<>();
}
