package com.ProTeen.backend.shelter.repository;

import com.ProTeen.backend.shelter.entity.Feedback;
import com.ProTeen.backend.shelter.entity.Shelter;
import com.ProTeen.backend.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface JPAFeedbackRepository extends JpaRepository<Feedback,Long> {
    List<Feedback> findByShelter(Shelter shelter);
    List<Feedback> findByUser(UserEntity user);

    Optional<Feedback> findByShelterAndUser(Shelter shelter,UserEntity user);
}
