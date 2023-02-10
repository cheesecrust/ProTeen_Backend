package com.ProTeen.backend.shelter.repository;

import com.ProTeen.backend.shelter.entity.Feedback;
import com.ProTeen.backend.shelter.entity.Shelter;
import com.ProTeen.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface JPAFeedbackRepository extends JpaRepository<Feedback,Long> {
    List<Feedback> findByShelter(Shelter shelter);
    List<Feedback> findByUser(User user);

    Optional<Feedback> findByShelterAndUser(Shelter shelter,User user);
}
