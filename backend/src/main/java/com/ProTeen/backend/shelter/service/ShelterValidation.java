package com.ProTeen.backend.shelter.service;


import com.ProTeen.backend.shelter.entity.Feedback;
import com.ProTeen.backend.shelter.entity.Shelter;
import com.ProTeen.backend.shelter.repository.JPAFeedbackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
// 인풋의 형태가 옳은지 여부 확인
public class ShelterValidation {
    private final JPAFeedbackRepository feedbackRepository;
    public void feedbackValidation(final Feedback feedback,String method){

        if(feedback == null){
            log.warn("Unknown entity");
            throw new RuntimeException("Unknown entity");
        }
        if(feedback.getComment() == null) {
            log.warn("Unknown comment");
            throw new RuntimeException("Unknown comment");
        }
        if(feedback.getScore() == null){
            log.warn("Unknown score");
            throw new RuntimeException("Unknown score");
        }
        if(feedbackRepository.findByShelterAndUser(feedback.getShelter(),feedback.getUser()).isPresent()){
            if(!"create".equals(method)){
                return;
            }
            else {
                log.warn("already exist");
                throw new RuntimeException("already exist");
            }
        }
    }
    public void feedbackUserValidation(final Feedback feedback, String user_Id){
        if (!feedback.getUser().getId().equals(user_Id)){
            log.info("Not Match userId");
            throw new RuntimeException("Not Match userId");
        }
    }
    public static void shelterValidation(final Shelter shelter){
        // 비즈니스 로직이므로 딱히 필요 x
    }

}
