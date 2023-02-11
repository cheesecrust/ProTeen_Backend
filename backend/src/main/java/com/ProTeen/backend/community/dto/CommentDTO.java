package com.ProTeen.backend.community.dto;

import com.ProTeen.backend.community.model.CommentEntity;
import com.ProTeen.backend.community.model.BoardEntity;
import lombok.*;

import java.time.LocalDateTime;

public class CommentDTO {
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request{
        private Long id;
        private String author;
        private String content;
        private LocalDateTime createdTime;
        private LocalDateTime modifiedTime;
        private BoardEntity board;
        public Request(final CommentEntity entity) {
            this.id = entity.getId();
            this.author = entity.getAuthor();
            this.content = entity.getContent();
            this.createdTime = entity.getCreatedTime();
            this.modifiedTime = entity.getModifiedTime();
            this.board = entity.getBoard();
        }

        public static CommentEntity toEntity(final CommentDTO.Request dto){
            return CommentEntity.builder()
                    .id(dto.getId())
                    .author(dto.getAuthor())
                    .content(dto.getContent())
                    .createdTime(dto.getCreatedTime())
                    .modifiedTime(dto.getModifiedTime())
                    .board(dto.getBoard())
                    .build();
        }
    }

    @Getter
    public static class Response{
        private final Long id;
        private final String author;
        private final String content;
        private final LocalDateTime createdTime;
        private final LocalDateTime modifiedTime;

        public Response(final CommentEntity entity) {
            this.id = entity.getId();
            this.author = entity.getAuthor();
            this.content = entity.getContent();
            this.createdTime = entity.getCreatedTime();
            this.modifiedTime = entity.getModifiedTime();
        }

    }
}
