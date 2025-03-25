package com.example.Store.repository;

import com.example.Store.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByStoreId(Long storeId);
    List<Reservation> findByUserId(Long userId);
}
