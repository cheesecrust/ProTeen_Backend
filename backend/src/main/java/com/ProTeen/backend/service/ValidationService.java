package com.ProTeen.backend.service;

import com.ProTeen.backend.model.BoardEntity;
import com.ProTeen.backend.model.CommentEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidationService {

    public static void boardValidate(final BoardEntity entity){
        if(entity == null){
            log.warn("Unknown entity");
            throw new RuntimeException("Unknown entity");
        }
        if(entity.getContent() == null){
            log.warn("Unknown content");
            throw new RuntimeException("Unknown content");
        }
        if(entity.getAuthor() == null){
            log.warn("Unknown author");
            throw new RuntimeException("Unknown author");
        }

        if(entity.getTitle() == null){
            log.warn("Unknown title");
            throw new RuntimeException("Unknown title");
        }
    }

    public static void commentValidate(final CommentEntity entity){
        if(entity == null){
            log.warn("Unknown entity");
            throw new RuntimeException("Unknown entity");
        }
        if(entity.getContent() == null){
            log.warn("Unknown content");
            throw new RuntimeException("Unknown content");
        }
    }


    public static void boardMatchUser(final String userId, final Optional<BoardEntity> original){
        if(!userId.equals((original.get().getUserId()))){
            log.info("different");
            throw new RuntimeException("글쓴이와 사용자가 다릅니다.");
        }
    }

    public static void commentMatchUser(final String userId, final Optional<CommentEntity> original){
        if(!userId.equals((original.get().getUserId()))){
            log.info("different");
            throw new RuntimeException("글쓴이와 사용자가 다릅니다.");
        }
    }


}
