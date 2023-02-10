package com.ProTeen.backend.faq.service;

import com.ProTeen.backend.faq.dto.FaqDTO;
import com.ProTeen.backend.faq.entity.Faq;

import java.util.List;
import java.util.Optional;

public interface FaqService {
    Optional<Faq> searchById(String category);
    List<FaqDTO> searchAll();
    List<String> addNewFaq(String category,String info);
}
