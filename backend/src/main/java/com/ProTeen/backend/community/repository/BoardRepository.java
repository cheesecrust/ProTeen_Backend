package com.ProTeen.backend.community.repository;

import com.ProTeen.backend.community.model.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    List<BoardEntity> findByAuthor(String author);
    List<BoardEntity> findByCategory(String category);

    Page<BoardEntity> findByTitleContainingIgnoreCase(String titleKeyWord, Pageable pageable);

    @Modifying
    @Query("UPDATE BoardEntity SET view = view + 1 where id = :id")
    int updateView(@Param(value = "id") Long postId);
}
