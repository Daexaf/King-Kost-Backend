package com.enigma.kingkost.controllers;

import com.enigma.kingkost.constant.AppPath;
import com.enigma.kingkost.dto.request.ReviewRequest;
import com.enigma.kingkost.dto.response.ReviewResponse;
import com.enigma.kingkost.entities.Review;
import com.enigma.kingkost.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = AppPath.URL_CROSS)
@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.REVIEW)
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/v1")
    public ResponseEntity<ReviewResponse> createReview(@RequestBody ReviewRequest reviewRequest) {
        ReviewResponse createdReview = reviewService.createReview(reviewRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    @GetMapping("/v1")
    public List<ReviewResponse> getReviews() {
        return reviewService.getAll();
    }

    @GetMapping("/v1/{id}")
    public ReviewResponse getReviewById(@PathVariable String id) {
        return reviewService.getById(id);
    }

    @DeleteMapping("/v1/{id}")
    public void deleteReviewById(@PathVariable String id) {
        reviewService.deleteReview(id);
    }

    @PutMapping("/v1")
    public ResponseEntity<ReviewResponse> updateReview(@RequestBody ReviewRequest reviewRequest) {
        ReviewResponse updatedReview = reviewService.update(reviewRequest);
        if (updatedReview != null) {
            return ResponseEntity.ok(updatedReview);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
