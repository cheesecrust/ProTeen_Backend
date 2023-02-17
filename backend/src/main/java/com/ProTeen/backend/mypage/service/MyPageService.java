package com.ProTeen.backend.mypage.service;

import com.ProTeen.backend.community.model.BoardEntity;
import com.ProTeen.backend.community.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyPageService {

    @Autowired
    private BoardRepository boardRepository;

    public List<BoardEntity> getMyBoardList(String id) {
        return boardRepository.findByUserId(id);
    }

}
