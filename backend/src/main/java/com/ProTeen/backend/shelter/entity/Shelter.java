package com.ProTeen.backend.shelter.entity;

import com.ProTeen.backend.shelter.dto.FeedbackDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shelter")
public class Shelter {

    @Id // Private key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동생성
    @Column(name = "Shelter_id")
    private Long id;

    private String rprsTelno;
    private String fxno;
    private String emlAddr;
    private String cpctCnt;
    private String etrTrgtCn;
    private String etrPrdCn;
    private String nrbSbwNm;
    private String nrbBusStnNm;
    private String crtrYmd;
    private String expsrYn;
    private String rmrkCn;
    private String fcltNm;
    private String operModeCn;
    private String fcltTypeNm;
    private String ctpvNm;
    private String sggNm;
    private String roadNmAddr;
    private String lotnoAddr;
    private String lat;
    private String lot;
    private String hmpgAddr;

    // 항상 다 가져와야함, 조회시 검색이 아님.
    @OneToMany(mappedBy = "shelter", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Feedback> feedbacks = new ArrayList<>();

    public List<FeedbackDTO.Response> getFeedbackResponse(){
        return feedbacks.stream().map(FeedbackDTO.Response::new).toList();
    }



}
