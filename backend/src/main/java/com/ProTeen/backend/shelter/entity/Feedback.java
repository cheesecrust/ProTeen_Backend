package com.ProTeen.backend.shelter.entity;
import com.ProTeen.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feedback")
public class Feedback {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;    // 댓글에 해당하는 쉘터.
    private Integer score;
    private String comment;
    private LocalDateTime modifiedTime;
    private LocalDateTime createdTime;
}
