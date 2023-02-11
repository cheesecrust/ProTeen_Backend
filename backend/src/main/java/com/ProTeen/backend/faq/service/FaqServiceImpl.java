package com.ProTeen.backend.faq.service;

import com.ProTeen.backend.faq.dto.FaqDTO;
import com.ProTeen.backend.faq.dto.FaqDtoRepository;
import com.ProTeen.backend.faq.entity.Faq;
import com.ProTeen.backend.faq.repository.JPAFaqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FaqServiceImpl implements FaqService{
    private final JPAFaqRepository faqRepository;
    private final FaqDtoRepository faqDtoRepository;

    public Optional<Faq> searchById(String category){
        return faqRepository.findById(category);
    }
    public List<FaqDTO> searchAll(){
        return faqDtoRepository.findAll();
    }
    public List<String> addNewFaq(String category,String info) {
        List<String> temp = faqRepository.findById(category).get().getInfo_list();
        temp.add(info);
        return temp;
    }
}
