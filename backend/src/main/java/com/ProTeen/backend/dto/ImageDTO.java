package com.ProTeen.backend.dto;

import com.ProTeen.backend.model.ImageEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

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
