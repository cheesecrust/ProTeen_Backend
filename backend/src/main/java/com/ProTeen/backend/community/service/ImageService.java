package com.ProTeen.backend.community.service;

import com.ProTeen.backend.community.model.ImageEntity;
import com.ProTeen.backend.community.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public void create(final ImageEntity entity){
        imageRepository.save(entity);
    }
}
