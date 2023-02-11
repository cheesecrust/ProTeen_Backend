package com.ProTeen.backend.community.dto;

import com.ProTeen.backend.community.model.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageDTO {

    private List<BoardDTO.Summary> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;

}
