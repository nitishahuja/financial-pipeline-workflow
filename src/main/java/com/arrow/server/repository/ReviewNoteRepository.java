package com.arrow.server.repository;

import com.arrow.server.model.ReviewDecision;
import com.arrow.server.model.ReviewNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewNoteRepository extends JpaRepository<ReviewNote, Long> {
    List<ReviewNote> findByProductId(Long productId);
    List<ReviewNote> findByProductIdAndReviewDecision(Long productId, ReviewDecision decision);
}