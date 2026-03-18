package com.arrow.server.service;

import com.arrow.server.dto.CreateReviewRequest;
import com.arrow.server.dto.ReviewNoteResponse;
import com.arrow.server.model.ReviewDecision;

import java.util.List;

public interface ReviewNoteService {
    ReviewNoteResponse addReview(Long productId, CreateReviewRequest request);
    List<ReviewNoteResponse> getReviewsByProduct(Long productId);
    List<ReviewNoteResponse> getReviewsByProductAndDecision(Long productId, ReviewDecision decision);
}