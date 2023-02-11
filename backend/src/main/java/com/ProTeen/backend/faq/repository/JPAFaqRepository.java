package com.ProTeen.backend.faq.repository;

import com.ProTeen.backend.faq.entity.Faq;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JPAFaqRepository extends JpaRepository<Faq, String> {
}
