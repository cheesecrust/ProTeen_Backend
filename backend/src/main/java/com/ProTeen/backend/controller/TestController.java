package com.ProTeen.backend.controller;

import com.ProTeen.backend.dto.BoardDTO;
import com.ProTeen.backend.dto.ResponseDTO;
import com.ProTeen.backend.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("board")
public class TestController {
    private final BoardService service;

    @PostMapping("/postTest")
    public ResponseEntity<?>  testPost(@RequestPart(value = "dto") BoardDTO.Total dto){
        System.out.println(dto.getTitle());
        System.out.println(dto.getAuthor());
        System.out.println(dto.getContent());
        System.out.println(dto.getCategory());
        return ResponseEntity.ok().body("success");
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
