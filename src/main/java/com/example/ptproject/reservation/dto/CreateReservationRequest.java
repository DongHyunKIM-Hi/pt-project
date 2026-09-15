package com.example.ptproject.reservation.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class CreateReservationRequest {

    @NotNull(message = "트레이너를 선택해야 합니다")
    private Long trainerId;

    @NotBlank(message = "회원 이름은 필수입니다")
    private String memberName;

    @NotNull(message = "예약 시간은 필수입니다")
    @Future(message = "예약 시간은 현재 이후여야 합니다")
    private LocalDateTime startTime;

    public Long getTrainerId() { return trainerId; }
    public String getMemberName() { return memberName; }
    public LocalDateTime getStartTime() { return startTime; }
}
