package com.ProTeen.backend.community.model;

import com.ProTeen.backend.community.dto.CommentDTO;
import com.ProTeen.backend.community.dto.ImageDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "board")
public class BoardEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "content")
    private String content;

    @Column(name = "category")
    private String category;

    @Column(name = "createTime", updatable = false)
    private LocalDateTime createTime;

    @Column(name = "modifiedTime")
    private LocalDateTime modifiedTime;

    @Column(name = "token")
    private String userId;

    @Column(columnDefinition = "integer default 0", name = "view")
    private int view;

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<ImageEntity> imageList = new ArrayList<>();

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<CommentEntity> comments = new ArrayList<CommentEntity>();

    public List<CommentDTO.Response> getCommentResponse(){
        return comments.stream().map(CommentDTO.Response::new).toList();
    }

    public List<ImageDTO> getImageResponse(){
        return imageList.stream().map(ImageDTO::new).toList();
    }
}
