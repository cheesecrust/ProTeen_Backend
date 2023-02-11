package com.ProTeen.backend.shelter.service;

import com.ProTeen.backend.shelter.entity.Feedback;
import com.ProTeen.backend.shelter.repository.JPAFeedbackRepository;
import com.ProTeen.backend.shelter.repository.JPAShelterRepository;
import com.ProTeen.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class FeedbackServiceImpl implements FeedbackService {
    private final ShelterValidation validation;
    private final JPAShelterRepository shelterRepository;
    private final UserRepository userRepository;
    private final JPAFeedbackRepository feedbackRepository;

    // 생성
    public String create(final Feedback feedback) {
        validation.feedbackValidation(feedback, "create");
        feedbackRepository.save(feedback);
        return "OK";
    }
    // 삭제
    public void remove(final Long shelter_Id,final String user_Id){
        try {
            final Optional<Feedback> feedbackDel = feedbackRepository.findByShelterAndUser(shelterRepository.findById(shelter_Id).get(),userRepository.findById(user_Id).get());
            validation.feedbackUserValidation(feedbackDel.get(),user_Id);
            feedbackRepository.deleteById(feedbackDel.get().getId());
        }
        catch (Exception e){
            log.error("error deleting feedback");
            throw new RuntimeException("error deleting feedback");
        }
    }
    // 수정
    public void update(final Long shelter_Id,final String user_Id, final Feedback feedback){
        try {
            final Optional<Feedback> feedbackUp = feedbackRepository.findByShelterAndUser(shelterRepository.findById(shelter_Id).get(),userRepository.findById(user_Id).get());
            validation.feedbackValidation(feedback,"put");
            validation.feedbackUserValidation(feedbackUp.get(),user_Id);
            final Feedback updateFeedback = feedbackUp.get();
            updateFeedback.setScore(feedback.getScore());
            updateFeedback.setComment(feedback.getComment());
            updateFeedback.setModifiedTime(feedback.getModifiedTime());
            feedbackRepository.save(updateFeedback);

        }
        catch (Exception e){
            log.error("error update feedback");
            throw new RuntimeException("error updating feedback");
        }
    }
}
