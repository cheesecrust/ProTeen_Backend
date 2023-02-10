package com.ProTeen.backend.shelter.controller;

import com.ProTeen.backend.shelter.dto.FeedbackDTO;
import com.ProTeen.backend.shelter.entity.Feedback;
import com.ProTeen.backend.shelter.repository.JPAFeedbackRepository;
import com.ProTeen.backend.shelter.repository.JPAShelterRepository;
import com.ProTeen.backend.shelter.service.FeedbackService;
import com.ProTeen.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static java.time.LocalDateTime.now;

@RestController
@RequiredArgsConstructor
@RequestMapping("feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final JPAShelterRepository shelterRepository;
    private final JPAFeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    @PostMapping("/post/{shelter_id}")
    public ResponseEntity<?> createFeedback(@AuthenticationPrincipal String userId, @RequestBody FeedbackDTO feedback, @PathVariable String shelter_id){
        try {
            // 직접 연결
            Feedback feedbackTemp = Feedback.builder()
                    .shelter(shelterRepository.findById(Long.parseLong(shelter_id)).get())
                    .user(userRepository.findById(userId).get())
                    .comment(feedback.getComment())
                    .score(feedback.getScore())
                    .createdTime(now())
                    .modifiedTime(now())
                    .build();
            feedbackService.create(feedbackTemp);
            return ResponseEntity.ok().body("created");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/delete/{shelter_id}")
    public ResponseEntity<?> deleteFeedback(@AuthenticationPrincipal String userId, @PathVariable String shelter_id){
        try{
            feedbackService.remove(Long.parseLong(shelter_id),userId);
            return  ResponseEntity.ok().body("removed");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/put/{shelter_id}")
    public ResponseEntity<?> updateFeedback(@AuthenticationPrincipal String userId, @RequestBody FeedbackDTO feedback, @PathVariable String shelter_id){
        try{
            final Optional<Feedback> feedbackUp = feedbackRepository.findByShelterAndUser(shelterRepository.findById(Long.parseLong(shelter_id)).get(),userRepository.findById(userId).get());
            // 있는걸로 연결
            Feedback feedbackTemp = Feedback.builder()
                    .shelter(feedbackUp.get().getShelter())
                    .user(feedbackUp.get().getUser())
                    .comment(feedback.getComment())
                    .score(feedback.getScore())
                    .createdTime(feedbackUp.get().getCreatedTime())
                    .modifiedTime(now())
                    .build();
            feedbackService.update(Long.parseLong(shelter_id),userId,feedbackTemp);
            return ResponseEntity.ok().body("updated");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
