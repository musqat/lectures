package com.example.Store.dto;

import com.example.Store.entity.Reservation;
import com.example.Store.entity.Store;
import com.example.Store.entity.User;
import com.example.Store.type.ReservationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDto {
    private Long id;
    private Long storeId;
    private Long userId;
    private LocalDateTime reservationTime;
    private ReservationStatus status;

    public static ReservationDto fromEntity(Reservation reservation) {
        return ReservationDto.builder()
                .id(reservation.getId())
                .storeId(reservation.getStore().getId())
                .userId(reservation.getUser().getId())
                .reservationTime(reservation.getReservationTime())
                .status(reservation.getStatus())
                .build();
    }

    public static Reservation toEntity(ReservationDto reservationDto, Store store, User user) {
        return Reservation.builder()
                .id(reservationDto.getId())
                .store(store)
                .user(user)
                .reservationTime(reservationDto.getReservationTime())
                .status(reservationDto.getStatus())
                .build();
    }
}
