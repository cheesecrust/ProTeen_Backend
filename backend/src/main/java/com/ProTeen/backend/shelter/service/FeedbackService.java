package com.ProTeen.backend.shelter.service;

import com.ProTeen.backend.shelter.entity.Feedback;

public interface FeedbackService {
    String create(final Feedback feedback);
    void remove(final Long feedbackId, final String user_Id);
    void update(final Long feedbackId, final String user_Id, final Feedback feedback);

}
