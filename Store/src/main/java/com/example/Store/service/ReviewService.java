package com.example.Store.service;

import com.example.Store.dto.ReviewDto;
import com.example.Store.entity.Review;
import com.example.Store.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 리뷰 생성 및 조회 기능을 제공합니다.
 */
@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    /**
     * 새로운 리뷰를 생성합니다.
     */
    @Transactional
    public ReviewDto createReview(ReviewDto reviewDto) {
        Review review = ReviewDto.toEntity(reviewDto);
        return ReviewDto.fromEntity(reviewRepository.save(review));
    }

    /**
     * 특정 가게의 리뷰 목록을 조회합니다.
     */
    @Transactional(readOnly = true)
    public List<ReviewDto> getReviewsByStore(Long storeId) {
        return reviewRepository.findByStoreId(storeId).stream()
                .map(ReviewDto::fromEntity)
                .collect(Collectors.toList());
    }
}
