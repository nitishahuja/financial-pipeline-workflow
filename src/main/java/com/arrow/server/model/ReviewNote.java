package com.arrow.server.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "review_notes")
public class ReviewNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private String reviewerName;

    @Column
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewDecision reviewDecision;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public ReviewNote() {}

    public ReviewNote(Product product, String reviewerName, String comment, ReviewDecision reviewDecision) {
        this.product = product;
        this.reviewerName = reviewerName;
        this.comment = comment;
        this.reviewDecision = reviewDecision;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
