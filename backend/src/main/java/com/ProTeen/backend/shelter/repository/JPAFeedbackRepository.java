package com.ProTeen.backend.shelter.repository;

import com.ProTeen.backend.shelter.domain.Feedback;
import com.ProTeen.backend.shelter.domain.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface JPAFeedbackRepository extends JpaRepository<Feedback,Long> {
    List<Feedback> findByShelter(Shelter shelter);
}
