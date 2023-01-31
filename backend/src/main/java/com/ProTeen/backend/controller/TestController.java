package com.ProTeen.backend.controller;

import com.ProTeen.backend.dto.ResponseDTO;
import com.ProTeen.backend.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("board")
public class TestController {
    private final BoardService service;

    @GetMapping
    public String testController(){
        return "test";
    }

    @GetMapping("/test")
    public ResponseEntity<?> testBoard(){
        String str = service.testService();
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.ok().body(response);
    }
}
