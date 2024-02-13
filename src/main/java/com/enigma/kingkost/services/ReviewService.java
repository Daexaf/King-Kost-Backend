package com.enigma.kingkost.services;

import com.enigma.kingkost.dto.request.ReviewRequest;
import com.enigma.kingkost.dto.response.ReviewResponse;

import java.util.List;

public interface ReviewService {

    ReviewResponse createReview(ReviewRequest reviewRequest);

    ReviewResponse update(ReviewRequest reviewRequest);

    void deleteReview(String id);

    List<ReviewResponse> getAll();

    ReviewResponse getById(String id);
}
