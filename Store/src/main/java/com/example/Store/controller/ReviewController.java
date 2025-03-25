package com.example.Store.controller;

import com.example.Store.dto.ReviewDto;
import com.example.Store.service.ReviewService;
import com.example.Store.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    @Autowired
    public ReviewController(ReviewService reviewService, UserService userService) {
        this.reviewService = reviewService;
        this.userService = userService;
    }

    /**
     * 새로운 리뷰를 생성
     */
    @PostMapping("/create")
    public ReviewDto createReview(@RequestBody ReviewDto reviewDto, HttpServletRequest request) {
        reviewDto.setUserId(userService.getUserIdFromRequest(request));
        return reviewService.createReview(reviewDto);
    }

    /**
     * 특정 가게의 리뷰 목록을 조회
     */
    @GetMapping("/store/{storeId}")
    public List<ReviewDto> getReviewsByStore(@PathVariable Long storeId) {
        return reviewService.getReviewsByStore(storeId);
    }
}
