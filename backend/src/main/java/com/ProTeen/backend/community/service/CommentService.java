package com.ProTeen.backend.community.service;

import com.ProTeen.backend.community.model.CommentEntity;
import com.ProTeen.backend.community.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;

    public List<CommentEntity> create(final CommentEntity entity){
        ValidationService.commentValidate(entity);

        commentRepository.save(entity);

        return commentRepository.findByAuthor(entity.getAuthor());
    }
    public void update(final String userId, final Long id, final CommentEntity entity){ // comment id

        final Optional<CommentEntity> original = commentRepository.findById(id); // Id 필요

        ValidationService.commentValidate(entity);
        ValidationService.commentMatchUser(userId, original);

        if(original.isPresent()){
            final CommentEntity comment = original.get();
            comment.setAuthor(entity.getAuthor());
            comment.setContent(entity.getContent());
            comment.setModifiedTime(LocalDateTime.now());
            commentRepository.save(comment);
        }
    }

    public void delete(final String userId, final Long id){
        try{
            final Optional<CommentEntity> original = commentRepository.findById(id); // Id 필요

            ValidationService.commentMatchUser(userId,original);

            commentRepository.deleteById(id);

        }catch (Exception e){
            log.error("error deleting entity ");

            throw new RuntimeException("error deleting entity " + id);
        }
    }
}
