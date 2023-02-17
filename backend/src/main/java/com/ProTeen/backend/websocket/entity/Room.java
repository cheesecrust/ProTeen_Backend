package com.ProTeen.backend.websocket.entity;

import com.ProTeen.backend.user.model.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@AllArgsConstructor
@ToString
@Builder
@Setter
public class Room {

    @Id
    private String id;

    @Column
    private String createdAt;

    @Column
    private boolean isOpen;

    @Column
    private int cnt;

    @Column
    private String role;

    // 방장
    @OneToOne
    @JoinColumn(referencedColumnName = "nickname")
    private UserEntity host;

    @Column
    private String participant;
}