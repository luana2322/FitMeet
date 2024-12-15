package com.FitMeet.service;

import com.FitMeet.model.Review;

import java.util.List;

public interface ReviewService {
    List<Review> findAll();

    Review findById(Long id);

    void deteleById(Long id);

    Review save(Review attachment);

    Review update(Review attachment);
}
