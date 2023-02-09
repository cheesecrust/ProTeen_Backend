package com.ProTeen.backend.community.service;

import com.ProTeen.backend.community.model.CommentEntity;
import com.ProTeen.backend.community.model.BoardEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
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

        List<String> categoryList = Arrays.asList("information", "question", "help", "free");
        if(entity.getCategory() == null || !categoryList.contains(entity.getCategory())){
            log.warn("Unknown category");
            throw new RuntimeException("Unknown category");
        }
    }

    public static void commentValidate(final CommentEntity entity){
        if(entity == null){
            log.warn("Unknown entity");
            throw new RuntimeException("Unknown entity");
        }
        if(entity.getAuthor() == null){
            log.warn("Unknown author");
            throw new RuntimeException("Unknown author");
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
