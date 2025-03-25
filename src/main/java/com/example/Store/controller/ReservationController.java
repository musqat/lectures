package com.example.Store.controller;

import com.example.Store.dto.ReservationDto;
import com.example.Store.service.ReservationService;
import com.example.Store.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final UserService userService;

    @Autowired
    public ReservationController(ReservationService reservationService, UserService userService) {
        this.reservationService = reservationService;
        this.userService = userService;
    }

    /**
     * 새로운 예약을 생성
     */
    @PostMapping("/create")
    public ReservationDto createReservation(@RequestBody ReservationDto reservationDto, HttpServletRequest request) {
        Long userId = userService.getUserIdFromRequest(request);
        reservationDto.setUserId(userId);
        return reservationService.createReservation(reservationDto);
    }

    /**
     * 특정 가게의 예약 목록을 조회
     */
    @GetMapping("/store/{storeId}")
    public List<ReservationDto> getReservationsByStore(@PathVariable Long storeId) {
        return reservationService.getReservationsByStore(storeId);
    }

    /**
     * 특정 사용자의 예약 목록을 조회
     */
    @GetMapping("/user")
    public List<ReservationDto> getReservationsByUser(HttpServletRequest request) {
        Long userId = userService.getUserIdFromRequest(request);
        return reservationService.getReservationsByUser(userId);
    }

    /**
     * 예약을 승인
     */
    @PostMapping("/approve/{reservationId}")
    public ReservationDto approveReservation(@PathVariable Long reservationId) {
        return reservationService.approveReservation(reservationId);
    }

    /**
     * 예약을 거부

     */
    @PostMapping("/reject/{reservationId}")
    public ReservationDto rejectReservation(@PathVariable Long reservationId) {
        return reservationService.rejectReservation(reservationId);
    }

    /**
     * 예약을 확인
     */
    @PostMapping("/check-in/{reservationId}")
    public String checkInReservation(@PathVariable Long reservationId) {
        return reservationService.checkInReservation(reservationId);
    }
}
