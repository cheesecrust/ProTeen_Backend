package com.ProTeen.backend.shelter.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Shelter {

    @Id // Private key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동생성
    @Column(name = "Shelter_id")
    private Long id;

    private String rprsTelno;
    private String fxno;
    private String emlAddr;
    private int cpctCnt;
    private String etrTrgtCn;
    private String etrPrdCn;
    private String nrbSbwNm;
    private String nrbBusStnNm;
    private String crtrYmd;
    private boolean expsrYn;
    private String rmrkCn;
    private String fcltNm;
    private String operModeCn;
    private String fcltTypeNm;
    private String ctpvNm;
    private String sggNm;
    private String roadNmAddr;
    private String lotnoAddr;
    private double lat;
    private double lot;
    private String hmpgAddr;
}
