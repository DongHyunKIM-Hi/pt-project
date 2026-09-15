package com.example.ptproject.reservation.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class UpdateReservationRequest {

    @NotNull(message = "변경할 예약 시간은 필수입니다")
    @Future(message = "예약 시간은 현재 이후여야 합니다")
    private LocalDateTime startTime;

    public LocalDateTime getStartTime() { return startTime; }
}
