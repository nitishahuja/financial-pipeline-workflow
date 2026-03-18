package com.arrow.server.dto;

import com.arrow.server.model.ReviewDecision;

import java.time.LocalDateTime;

public class ReviewNoteResponse {
    private Long id;
    private Long productId;
    private String reviewerName;
    private String comment;
    private ReviewDecision reviewDecision;
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ReviewDecision getReviewDecision() {
        return reviewDecision;
    }

    public void setReviewDecision(ReviewDecision reviewDecision) {
        this.reviewDecision = reviewDecision;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
