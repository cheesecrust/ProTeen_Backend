package com.ProTeen.backend.service;

import com.ProTeen.backend.model.CommentEntity;
import com.ProTeen.backend.model.ImageEntity;
import com.ProTeen.backend.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public void create(final ImageEntity entity){
        imageRepository.save(entity);
    }
}
