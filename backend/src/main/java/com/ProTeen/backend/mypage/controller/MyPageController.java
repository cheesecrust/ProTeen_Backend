package com.ProTeen.backend.mypage.controller;

import com.ProTeen.backend.community.dto.BoardDTO;
import com.ProTeen.backend.community.model.BoardEntity;
import com.ProTeen.backend.mypage.dto.MyPageDTO;
import com.ProTeen.backend.mypage.service.MyPageService;
import com.ProTeen.backend.self_diagnosis.dto.DiagnosisResultDTO;
import com.ProTeen.backend.self_diagnosis.service.DiagnosisResultServiceImpl;
import com.ProTeen.backend.user.security.TokenProvider;
import com.ProTeen.backend.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class MyPageController {

    @Autowired
    private MyPageService myPageService;

    @Autowired
    private UserService userService;

    @Autowired
    private DiagnosisResultServiceImpl diagnosisResultService;

    @GetMapping("/main/mypage")
    public ResponseEntity<?> getMyPage(@AuthenticationPrincipal String id) {

        String nickname = userService.getNicknameById(id);
        String userId = userService.getUserIdById(id);

        List<BoardEntity> myBoardEntityList = myPageService.getMyBoardList(id);
        List<BoardDTO.Summary> myBoardDTOsList = myBoardEntityList.stream().map(BoardDTO.Summary::new).toList();

        List<DiagnosisResultDTO> myDiagnosisResultDTOsList = diagnosisResultService.searchResultDTOByUser(id);

        MyPageDTO myPageDTO = MyPageDTO.builder()
                .userId(userId)
                .nickname(nickname)
                .myBoardDTOsList(myBoardDTOsList)
                .myDiagnosisResultDTOList(myDiagnosisResultDTOsList)
                .build();

        return ResponseEntity.ok().body(myPageDTO);
    }
}
