package com.ProTeen.backend.shelter.dto;

import com.ProTeen.backend.shelter.entity.Feedback;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
public class FeedbackDTO {
    private String comment;
    private Integer score;

    public FeedbackDTO(String comment, Integer score) {
        this.comment = comment;
        this.score = score;
    }
    @Getter
    public static class Response{
        private final String comment;
        private final Integer score;
        private final LocalDateTime modifiedTime;
        private final LocalDateTime createdTime;
        public Response(final Feedback feedback){
            this.comment = feedback.getComment();
            this.score = feedback.getScore();
            this.createdTime = feedback.getCreatedTime();
            this.modifiedTime = feedback.getModifiedTime();
        }
    }


}
