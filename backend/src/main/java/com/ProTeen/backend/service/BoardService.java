package com.ProTeen.backend.service;

import com.ProTeen.backend.model.BoardEntity;
import com.ProTeen.backend.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository repository;
    public String testService(){
        BoardEntity entity = BoardEntity.builder().title("first board item").build();
        repository.save(entity);
        BoardEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getTitle();
    }

    public List<BoardEntity> create(final BoardEntity entity){
        //Validation
        ValidationService.boardValidate(entity);

        repository.save(entity);

        log.info("Entity Id : {} is saved.", entity.getId());
        log.info("Entity createdTime : {}", entity.getCreateTime());
        log.info("Entity modifiedTime : {}", entity.getModifiedTime());
        log.info("Entity Id : {} is saved.", entity.getId());

        return repository.findByAuthor(entity.getAuthor());
    }

    public List<BoardEntity> retrieve(){
        return repository.findAll();
    }

    public BoardEntity read(Long id){return repository.findById(id).get();}

    public List<BoardEntity> update(final String userId, final Long id, final BoardEntity entity){
        final Optional<BoardEntity> original = repository.findById(id); // Id 필요

        ValidationService.boardMatchUser(userId, original);
        ValidationService.boardValidate(entity);

        final BoardEntity board = original.get();
        board.setTitle(entity.getTitle());
        board.setAuthor(entity.getAuthor());
        board.setContent(entity.getContent());
        board.setModifiedTime(LocalDateTime.now());
        repository.save(board);

        return retrieve();
    }

    public List<BoardEntity> delete(final String userId, final Long id){

        final Optional<BoardEntity> original = repository.findById(id); // Id 필요

        ValidationService.boardMatchUser(userId, original);

        try{
            repository.deleteById(id);

            System.out.println("delete");
        }catch (Exception e){
            log.error("error deleting entity");

            throw new RuntimeException("error deleting entity " + id);
        }

        return retrieve();
    }
}
