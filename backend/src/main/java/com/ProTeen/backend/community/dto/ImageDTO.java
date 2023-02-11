package com.ProTeen.backend.community.dto;

import com.ProTeen.backend.community.model.ImageEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {
    private String imgPath;
    public ImageDTO(final ImageEntity entity){
        this.imgPath = "localhost:8080/board/downloadFile/" + entity.getImgPath();
    }
}
