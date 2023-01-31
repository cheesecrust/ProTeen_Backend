package com.ProTeen.backend.model;

import com.ProTeen.backend.dto.CommentDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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

//    @GeneratedValue(generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")
//    private String id;


    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "content")
    private String content;

    @Column(name = "createTime", updatable = false)
    private LocalDateTime createTime;

    @Column(name = "modifiedTime")
    private LocalDateTime modifiedTime;

    @Column(name = "token")
    private String userId;
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<CommentEntity> comments = new ArrayList<CommentEntity>();

    public List<CommentDTO.Response> getCommentResponse(){
        return comments.stream().map(CommentDTO.Response::new).toList();
    }
}
