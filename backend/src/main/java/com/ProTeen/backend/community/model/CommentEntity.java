package com.ProTeen.backend.community.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment")
public class CommentEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author")
    private String author;

    @Column(name = "content")
    private String content;

    @Column(name = "createdTime")
    private LocalDateTime createdTime;

    @Column(name = "modifiedTime")
    private LocalDateTime modifiedTime;

    @Column(name = "token")
    private String userId;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private BoardEntity board;
}
