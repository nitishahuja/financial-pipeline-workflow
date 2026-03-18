package com.arrow.server.service;

import com.arrow.server.dto.CreateReviewRequest;
import com.arrow.server.dto.ReviewNoteResponse;
import com.arrow.server.exception.ResourceNotFoundException;
import com.arrow.server.model.Product;
import com.arrow.server.model.ReviewDecision;
import com.arrow.server.model.ReviewNote;
import com.arrow.server.repository.ProductRepository;
import com.arrow.server.repository.ReviewNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewNoteServiceImpl implements ReviewNoteService {

    @Autowired
    private ReviewNoteRepository reviewNoteRepository;

    @Autowired
    private ProductRepository productRepository;

    // ── helper: Entity → DTO ──────────────────────
    private ReviewNoteResponse toResponse(ReviewNote note) {
        ReviewNoteResponse response = new ReviewNoteResponse();
        response.setId(note.getId());
        response.setProductId(note.getProduct().getId());
        response.setReviewerName(note.getReviewerName());
        response.setComment(note.getComment());
        response.setReviewDecision(note.getReviewDecision());
        response.setCreatedAt(note.getCreatedAt());
        return response;
    }

    // ── ADD REVIEW ────────────────────────────────
    @Override
    @Transactional
    public ReviewNoteResponse addReview(Long productId, CreateReviewRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        if (request.getReviewerName() == null || request.getReviewerName().isBlank()) {
            throw new IllegalArgumentException("Reviewer name is required");
        }

        ReviewNote note = new ReviewNote(
                product,
                request.getReviewerName(),
                request.getComment(),
                request.getReviewDecision()
        );

        return toResponse(reviewNoteRepository.save(note));
    }

    // ── GET REVIEWS BY PRODUCT ────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<ReviewNoteResponse> getReviewsByProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product not found with id: " + productId);
        }
        return reviewNoteRepository.findByProductId(productId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ── GET REVIEWS BY PRODUCT AND DECISION ───────
    @Override
    @Transactional(readOnly = true)
    public List<ReviewNoteResponse> getReviewsByProductAndDecision(Long productId, ReviewDecision decision) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product not found with id: " + productId);
        }
        return reviewNoteRepository.findByProductIdAndReviewDecision(productId, decision)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}