package com.ProTeen.backend.shelter.domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Entity
@Getter@Setter
public class Feedback {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //EAGER
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;    // 댓글에 해당하는 쉘터.
    private Score score;
    private String comment;
}
