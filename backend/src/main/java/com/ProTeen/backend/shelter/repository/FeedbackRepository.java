package com.ProTeen.backend.shelter.repository;

import com.ProTeen.backend.shelter.domain.Feedback;
import com.ProTeen.backend.shelter.domain.Shelter;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepository {
    Feedback save(Feedback feedback);
    List<Feedback> findAll();

    List<Feedback> findByShelter(Shelter shelter);

    Optional<Feedback> findById(long id);
}
