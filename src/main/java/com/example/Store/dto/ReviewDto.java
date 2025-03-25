package com.example.Store.dto;

import com.example.Store.entity.Review;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {
    private Long id;
    private Long storeId;
    private Long userId;
    private String content;
    private int rating;

    public static ReviewDto fromEntity(Review review) {
        return ReviewDto.builder()
                .id(review.getId())
                .storeId(review.getStore().getId())
                .userId(review.getUser().getId())
                .content(review.getContent())
                .rating(review.getRating())
                .build();
    }

    public static Review toEntity(ReviewDto reviewDto) {
        return Review.builder()
                .id(reviewDto.getId())
                .content(reviewDto.getContent())
                .rating(reviewDto.getRating())
                .build();
    }
}
