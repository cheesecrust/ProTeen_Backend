package com.ProTeen.backend.repository;

import com.ProTeen.backend.model.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    List<BoardEntity> findByAuthor(String author);

    List<BoardEntity> findByCategory(String category);
}
