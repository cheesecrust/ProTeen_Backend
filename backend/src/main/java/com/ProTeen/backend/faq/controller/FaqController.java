package com.ProTeen.backend.faq.controller;

import com.ProTeen.backend.faq.dto.FaqDTO;
import com.ProTeen.backend.faq.entity.Faq;
import com.ProTeen.backend.faq.repository.JPAFaqRepository;
import com.ProTeen.backend.faq.service.FaqService;
import com.ProTeen.backend.user.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("faq")
@RequiredArgsConstructor
public class FaqController {

    private final FaqService faqService;
    private final JPAFaqRepository faqRepository;
    @GetMapping("/")
    public ResponseEntity<?> searchAll(){
        try {
            ResponseDTO<FaqDTO> responseDTO = ResponseDTO.<FaqDTO>builder().data(faqService.searchAll()).build();
            return ResponseEntity.ok().body(responseDTO);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @GetMapping("/{category}")
    public ResponseEntity<?> searchByCategory(@PathVariable String category){
        try{
            return ResponseEntity.ok().body(faqService.searchById(category));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/post/{category}")
    public ResponseEntity<?> addNewFaq(@PathVariable String category, @RequestBody Map<String,String> infoMap){
        String info = infoMap.get("info");
        try {
            if(faqRepository.findById(category).isPresent()) {
                Faq temp = faqRepository.findById(category).get();
                temp.setInfo_list(faqService.addNewFaq(category,info));
                faqRepository.save(temp);
            }
            else{
                Faq temp = new Faq();
                temp.getInfo_list().add(info);
                temp.setCategory(category);
                faqRepository.save(temp);
            }
            return ResponseEntity.badRequest().body("created");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
