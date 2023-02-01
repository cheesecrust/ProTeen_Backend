package com.ProTeen.backend.controller;

import com.ProTeen.backend.dto.BoardDTO;
import com.ProTeen.backend.dto.ResponseDTO;
import com.ProTeen.backend.model.BoardEntity;
import com.ProTeen.backend.repository.BoardRepository;
import com.ProTeen.backend.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/post")
    public ResponseEntity<?> createBoard(@AuthenticationPrincipal String userId, @RequestBody BoardDTO.Total dto){
        try{
            BoardEntity entity = BoardDTO.Total.toEntity(dto);

            entity.setId(null);
            entity.setUserId(userId);

            entity.setCreateTime(LocalDateTime.now());
            entity.setModifiedTime(LocalDateTime.now());

            List<BoardEntity> entities = boardService.create(entity);

            List<BoardDTO.Summary> dtos = entities.stream().map(BoardDTO.Summary::new).toList();

            ResponseDTO<BoardDTO.Summary> response = ResponseDTO.<BoardDTO.Summary>builder().data(dtos).build();

            return ResponseEntity.ok().body("저장에 성공했습니다.");
        }catch (Exception e){
            String error = e.getMessage();
            ResponseDTO<BoardDTO> response = ResponseDTO.<BoardDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/read")
    public ResponseEntity<?> retrieveBoardList(){

        List<BoardEntity> entities = boardService.retrieve();

        List<BoardDTO.Summary> dtos = entities.stream().map(BoardDTO.Summary::new).toList();

        ResponseDTO<BoardDTO.Summary> response = ResponseDTO.<BoardDTO.Summary>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/read/category/{category}")
    public ResponseEntity<?> readByCategory(@PathVariable String category){

        List<String> categoryList = Arrays.asList("information", "question", "help", "free");
        if(!categoryList.contains(category)){
            return ResponseEntity.badRequest().body("wrong path");
        }

        List<BoardEntity> entities = boardService.retrieveByCategory(category);

        List<BoardDTO.Summary> dtos = entities.stream().map(BoardDTO.Summary::new).toList();

        ResponseDTO<BoardDTO.Summary> response = ResponseDTO.<BoardDTO.Summary>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/read/{id}")
    public ResponseEntity<?> read(@PathVariable Long id){
        try{
            BoardEntity entity = boardService.read(id);

            BoardDTO.Total response = new BoardDTO.Total(entity);

            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            String error = e.getMessage();

            ResponseDTO<BoardDTO> response = ResponseDTO.<BoardDTO>builder().error(error).build();

            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<?> updateBoard(@AuthenticationPrincipal String userId, @PathVariable Long id ,@RequestBody BoardDTO.Total dto){
        try{
            BoardEntity entity = BoardDTO.Total.toEntity(dto);

            System.out.println(userId);

            List<BoardEntity> entities = boardService.update(userId, id, entity);

            List<BoardDTO.Total> dtos = entities.stream().map(BoardDTO.Total::new).toList();

            ResponseDTO<BoardDTO.Total> response = ResponseDTO.<BoardDTO.Total>builder().data(dtos).build();

            return ResponseEntity.ok().body("수정에 성공하였습니다.");
        }catch (Exception e){
            String error = e.getMessage();

            ResponseDTO<BoardDTO> response = ResponseDTO.<BoardDTO>builder().error(error).build();

            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBoard(@AuthenticationPrincipal String userId, @PathVariable Long id){
        try {
            List<BoardEntity> entities = boardService.delete(userId, id);

            List<BoardDTO.Summary> dtos = entities.stream().map(BoardDTO.Summary::new).toList();

            ResponseDTO<BoardDTO.Summary> response = ResponseDTO.<BoardDTO.Summary>builder().data(dtos).build();

            return ResponseEntity.ok().body("삭제 성공하였습니다.");
        }catch (Exception e){
            System.out.println(e.getMessage());

            String error = e.getMessage();

            ResponseDTO<BoardDTO> response = ResponseDTO.<BoardDTO>builder().error(error).build();

            return ResponseEntity.badRequest().body(response);
        }
    }
}
