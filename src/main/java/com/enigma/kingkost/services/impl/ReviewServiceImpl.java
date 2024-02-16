package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.dto.request.ReviewRequest;
import com.enigma.kingkost.dto.response.ReviewResponse;
import com.enigma.kingkost.entities.Customer;
import com.enigma.kingkost.entities.Review;
import com.enigma.kingkost.repositories.CustomerRepository;
import com.enigma.kingkost.repositories.ReviewRepository;
import com.enigma.kingkost.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;

    @Override
    public ReviewResponse createReview(ReviewRequest reviewRequest) {
        Customer customer = customerRepository.findById(reviewRequest.getCustomerId()).orElse(null);
        Review review = reviewRepository.save(Review.builder()
                        .id(reviewRequest.getId())
                        .message(reviewRequest.getMessage())
                        .customerId(customer)
                .build());
        return convertToResponse(review);
    }

    @Override
    public ReviewResponse update(ReviewRequest reviewRequest) {
        Optional<Review> existingReview = reviewRepository.findById(reviewRequest.getId());

        if (existingReview.isPresent()) {
            Review review = existingReview.get();
            review.setMessage(reviewRequest.getMessage());

            if (!review.getCustomerId().getId().equals(reviewRequest.getCustomerId())) {
                Customer customer = customerRepository.findById(reviewRequest.getCustomerId()).orElse(null);
                if (customer != null) {
                    review.setCustomerId(customer);
                } else {
                    return null;
                }
            }
            Review updatedReview = reviewRepository.save(review);
            return convertToResponse(updatedReview);
        } else {
            return null;
        }
    }

    @Override
    public void deleteReview(String id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public List<ReviewResponse> getAll() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewResponse getById(String id) {
        Optional<Review> review = reviewRepository.findById(id);
        return review.map(this::convertToResponse).orElse(null);
    }

    private ReviewResponse convertToResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .message(review.getMessage())
                .customerId(review.getCustomerId())
                .build();
    }
}
