package com.example.Store.service;

import com.example.Store.dto.ReservationDto;
import com.example.Store.entity.Reservation;
import com.example.Store.entity.Store;
import com.example.Store.entity.User;
import com.example.Store.exception.ApiException;
import com.example.Store.repository.ReservationRepository;
import com.example.Store.repository.StoreRepository;
import com.example.Store.repository.UserRepository;
import com.example.Store.type.ErrorCode;
import com.example.Store.type.ReservationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 예약 생성, 조회, 승인, 거부 및 확인 기능을 제공합니다.
 */
@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * 새로운 예약을 생성합니다.
     */
    @Transactional
    public ReservationDto createReservation(ReservationDto dto) {
        Store store = storeRepository.findById(dto.getStoreId())
                .orElseThrow(() -> new ApiException(ErrorCode.STORE_NOT_FOUND));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        Reservation reservation = ReservationDto.toEntity(dto, store, user);
        reservation.setStatus(ReservationStatus.PENDING);

        return ReservationDto.fromEntity(reservationRepository.save(reservation));
    }

    /**
     * 특정 가게의 예약 목록을 조회합니다.
     */
    @Transactional(readOnly = true)
    public List<ReservationDto> getReservationsByStore(Long storeId) {
        return reservationRepository.findByStoreId(storeId).stream()
                .map(ReservationDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 특정 사용자의 예약 목록을 조회합니다.
     */
    @Transactional(readOnly = true)
    public List<ReservationDto> getReservationsByUser(Long userId) {
        return reservationRepository.findByUserId(userId).stream()
                .map(ReservationDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 예약을 승인합니다.
     */
    @Transactional
    public ReservationDto approveReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ApiException(ErrorCode.RESERVATION_NOT_FOUND));

        reservation.setStatus(ReservationStatus.APPROVED);
        return ReservationDto.fromEntity(reservationRepository.save(reservation));
    }

    /**
     * 예약을 거부합니다.
     */
    @Transactional
    public ReservationDto rejectReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ApiException(ErrorCode.RESERVATION_NOT_FOUND));

        reservation.setStatus(ReservationStatus.REJECTED);
        return ReservationDto.fromEntity(reservationRepository.save(reservation));
    }

    /**
     * 예약을 확인합니다.(10분)
     */
    @Transactional
    public String checkInReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ApiException(ErrorCode.RESERVATION_NOT_FOUND));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reservationTime = reservation.getReservationTime();

        if (reservationTime.minusMinutes(10).isBefore(now) && now.isBefore(reservationTime.plusMinutes(10))) {
            reservation.setStatus(ReservationStatus.CHECKED);
            reservationRepository.save(reservation);
            return "Check-in successful";
        } else {
            return String.valueOf(ErrorCode.RESERVATION_TIME_OUT);
        }
    }
}
