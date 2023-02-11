package com.ProTeen.backend.community.dto;

import com.ProTeen.backend.community.model.BoardEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


public class BoardDTO {
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Total{
        private Long id;
        private String title;
        private String author;
        private String content;
        private String category;
        private int view;
        private LocalDateTime createTime;
        private LocalDateTime modifiedTime;
        private List<CommentDTO.Response> comment;
        private List<ImageDTO> imageList;
        public Total(final BoardEntity entity) {
            this.id = entity.getId();
            this.title = entity.getTitle();
            this.author = entity.getAuthor();
            this.content = entity.getContent();
            this.category = entity.getCategory();
            this.view = entity.getView();
            this.createTime = entity.getCreateTime();
            this.modifiedTime = entity.getModifiedTime();
            this.imageList = entity.getImageResponse();
            this.comment = entity.getCommentResponse();
        }

        public static BoardEntity toEntity(final BoardDTO.Total dto){
            return BoardEntity.builder()
                    .id(dto.getId())
                    .title(dto.getTitle())
                    .author(dto.getAuthor())
                    .content(dto.getContent())
                    .category(dto.getCategory())
                    .view(dto.getView())
                    .createTime(dto.getCreateTime())
                    .modifiedTime(dto.getModifiedTime())
                    .build();
        }
    }

    @Getter
    public static class Summary{
        private final Long id;
        private final String title;
        private final String author;
        private final String category;
        private final int view;
        private final LocalDateTime createTime;
        private final LocalDateTime modifiedTime;


        public Summary(final BoardEntity entity) {
            this.id = entity.getId();
            this.title = entity.getTitle();
            this.author = entity.getAuthor();
            this.category = entity.getCategory();
            this.view = entity.getView();
            this.createTime = entity.getCreateTime();
            this.modifiedTime = entity.getModifiedTime();
        }

    }
}
