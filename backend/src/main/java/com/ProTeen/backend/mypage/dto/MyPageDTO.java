package com.ProTeen.backend.mypage.dto;

import com.ProTeen.backend.community.dto.BoardDTO;
import com.ProTeen.backend.self_diagnosis.dto.DiagnosisResultDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Builder
@Getter
public class MyPageDTO {
    private String userId;
    private String nickname;
    private List<BoardDTO.Summary> myBoardDTOsList;
    private List<DiagnosisResultDTO> myDiagnosisResultDTOList;
}
