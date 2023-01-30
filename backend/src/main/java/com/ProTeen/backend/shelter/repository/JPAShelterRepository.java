package com.ProTeen.backend.shelter.repository;

import com.ProTeen.backend.shelter.domain.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JPAShelterRepository extends JpaRepository<Shelter,Long> {
    List<Shelter> findByCtpvNm(String ctpvNm);
}
