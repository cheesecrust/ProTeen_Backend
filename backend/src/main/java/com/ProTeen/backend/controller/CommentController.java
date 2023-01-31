package com.ProTeen.backend.controller;

import com.ProTeen.backend.dto.CommentDTO;
import com.ProTeen.backend.dto.ResponseDTO;
import com.ProTeen.backend.model.CommentEntity;
import com.ProTeen.backend.repository.BoardRepository;
import com.ProTeen.backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("comment")
public class CommentController {
    private final CommentService commentService;
    private final BoardRepository boardRepository;

    @PostMapping("/create/{id}")
    public ResponseEntity<?> createComment(@AuthenticationPrincipal String userId, @PathVariable Long id, @RequestBody CommentDTO.Request dto){
        CommentEntity entity = CommentDTO.Request.toEntity(dto);

        entity.setId(null);
        entity.setBoard(boardRepository.findById(id).get());
        entity.setUserId(userId);

        entity.setCreatedTime(LocalDateTime.now());
        entity.setModifiedTime(LocalDateTime.now());

        List<CommentEntity> entities = commentService.create(entity);

        List<CommentDTO.Response> dtos = entities.stream().map(CommentDTO.Response::new).toList();

        ResponseDTO<CommentDTO.Response> response = ResponseDTO.<CommentDTO.Response>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<?> updateComment(@AuthenticationPrincipal String userId, @PathVariable Long id,@RequestBody CommentDTO.Request dto){
        try{
            String tmpAuthor = dto.getAuthor();

            CommentEntity entity = CommentDTO.Request.toEntity(dto);

            entity.setAuthor(tmpAuthor);

            commentService.update(userId, id, entity);

            return ResponseEntity.ok().body("수정 성공했습니다.");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteComment(@AuthenticationPrincipal String userId, @PathVariable Long id, @RequestBody CommentDTO.Request dto){
        try{
            String tmpAuthor = dto.getAuthor(); // 나중에 유저 인증
            CommentEntity entity = CommentDTO.Request.toEntity(dto);

            entity.setAuthor(tmpAuthor);

            commentService.delete(userId, id);

            return ResponseEntity.ok().body("삭제 성공했습니다.");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("삭제에 실패했습니다.");
        }
    }
}
