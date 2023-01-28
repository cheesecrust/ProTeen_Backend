// User가 추가되면 User 테이블 이용 예정
package com.ProTeen.backend.shelter.domain;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Getter @Setter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

}
