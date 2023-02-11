package com.ProTeen.backend.service;

import com.ProTeen.backend.community.repository.BoardRepository;
import com.ProTeen.backend.community.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTest {
    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void 글쓰기(){
    }
}
