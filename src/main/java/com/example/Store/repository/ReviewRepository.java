package com.example.Store.repository;

import com.example.Store.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    // 특정 가게의 리뷰 목록을 조회하는 메서드
    List<Review> findByStoreId(Long storeId);
}
