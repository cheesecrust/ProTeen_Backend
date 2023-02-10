package com.ProTeen.backend.shelter.dto;

import com.ProTeen.backend.shelter.entity.Shelter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class ShelterFeedbackDTO {
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Total{
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
        private List<FeedbackDTO.Response> feedback;

        public Total(Shelter shelter) {
            this.id = shelter.getId();
            this.rprsTelno = shelter.getRprsTelno();
            this.fxno = shelter.getFxno();
            this.emlAddr = shelter.getEmlAddr();
            this.cpctCnt = shelter.getCpctCnt();
            this.etrTrgtCn = shelter.getEtrTrgtCn();
            this.etrPrdCn = shelter.getEtrPrdCn();
            this.nrbSbwNm = shelter.getNrbSbwNm();
            this.nrbBusStnNm = shelter.getNrbBusStnNm();
            this.crtrYmd = shelter.getCrtrYmd();
            this.expsrYn = shelter.getExpsrYn();
            this.rmrkCn = shelter.getRmrkCn();
            this.fcltNm = shelter.getFcltNm();
            this.operModeCn = shelter.getOperModeCn();
            this.fcltTypeNm = shelter.getFcltTypeNm();
            this.ctpvNm = shelter.getCtpvNm();
            this.sggNm = shelter.getSggNm();
            this.roadNmAddr = shelter.getRoadNmAddr();
            this.lotnoAddr = shelter.getLotnoAddr();
            this.lat = shelter.getLat();
            this.lot = shelter.getLot();
            this.hmpgAddr = shelter.getHmpgAddr();
            this.feedback = shelter.getFeedbackResponse();
        }
    }

        public static Shelter toEntity(final ShelterFeedbackDTO.Total dto){
            return Shelter.builder()
                    .id(dto.getId())
                    .rprsTelno(dto.getRprsTelno())
                    .fxno(dto.getFxno())
                    .emlAddr(dto.getEmlAddr())
                    .cpctCnt(dto.getCtpvNm())
                    .etrTrgtCn(dto.getEtrTrgtCn())
                    .etrPrdCn(dto.getEtrPrdCn())
                    .nrbSbwNm(dto.getNrbSbwNm())
                    .nrbBusStnNm(dto.getNrbBusStnNm())
                    .crtrYmd(dto.getCrtrYmd())
                    .expsrYn(dto.getExpsrYn())
                    .rmrkCn(dto.getRmrkCn())
                    .fcltNm(dto.getFcltNm())
                    .operModeCn(dto.getOperModeCn())
                    .fcltTypeNm(dto.getFcltTypeNm())
                    .ctpvNm(dto.getCtpvNm())
                    .sggNm(dto.getSggNm())
                    .roadNmAddr(dto.getRoadNmAddr())
                    .lotnoAddr(dto.getLotnoAddr())
                    .lat(dto.getLat())
                    .lot(dto.getLot())
                    .hmpgAddr(dto.getHmpgAddr())
                    .build();
    }

}


