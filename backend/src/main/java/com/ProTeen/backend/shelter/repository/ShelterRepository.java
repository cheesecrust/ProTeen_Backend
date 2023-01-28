package com.ProTeen.backend.shelter.repository;

import com.ProTeen.backend.shelter.domain.Shelter;

import java.util.List;
import java.util.Optional;

public interface ShelterRepository {
    Shelter save(Shelter shelter);
    Optional<Shelter> findById(Long id);
    List<Shelter> findAllShelterPagination(int start,int number);
    List<Shelter> findByCtpvNm(String ctpvNm);
    List<Shelter> findByCtpvNmPagination(String ctpvNm,int start,int number);



}
