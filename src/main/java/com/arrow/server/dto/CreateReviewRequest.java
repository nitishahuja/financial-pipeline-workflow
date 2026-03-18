package com.arrow.server.dto;

import com.arrow.server.model.ReviewDecision;

public class CreateReviewRequest {
    private String reviewerName;
    private String comment;
    private ReviewDecision reviewDecision;

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
}
