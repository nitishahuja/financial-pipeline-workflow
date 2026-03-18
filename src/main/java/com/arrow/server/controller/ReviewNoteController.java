package com.arrow.server.controller;

import com.arrow.server.dto.CreateReviewRequest;
import com.arrow.server.dto.ReviewNoteResponse;
import com.arrow.server.model.ReviewDecision;
import com.arrow.server.service.ReviewNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ReviewNoteController {

    @Autowired
    private ReviewNoteService reviewNoteService;

    // POST /api/products/{id}/reviews
    @PostMapping("/{id}/reviews")
    public ResponseEntity<ReviewNoteResponse> addReview(
            @PathVariable Long id,
            @RequestBody CreateReviewRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewNoteService.addReview(id, request));
    }

    // GET /api/products/{id}/reviews
    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<ReviewNoteResponse>> getReviews(
            @PathVariable Long id,
            @RequestParam(required = false) ReviewDecision decision) {
        if (decision != null) {
            return ResponseEntity.ok(reviewNoteService.getReviewsByProductAndDecision(id, decision));
        }
        return ResponseEntity.ok(reviewNoteService.getReviewsByProduct(id));
    }
}