package com.ProTeen.backend.shelter.repository;

import com.ProTeen.backend.shelter.entity.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JPAShelterRepository extends JpaRepository<Shelter,Long> {
    List<Shelter> findByCtpvNmContaining (String ctpvNm);
    List<Shelter> findByFcltNmContaining (String fcltNm);
}
