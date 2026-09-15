package com.example.ptproject.reservation.dto;

import com.example.ptproject.reservation.Reservation;

import java.time.LocalDateTime;

public class ReservationResponse {

    private final long id;
    private final long trainerId;
    private final String memberName;
    private final LocalDateTime startTime;

    private ReservationResponse(long id, long trainerId, String memberName, LocalDateTime startTime) {
        this.id = id;
        this.trainerId = trainerId;
        this.memberName = memberName;
        this.startTime = startTime;
    }

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
            reservation.getId(), reservation.getTrainerId(),
            reservation.getMemberName(), reservation.getStartTime()
        );
    }

    public long getId() { return id; }
    public long getTrainerId() { return trainerId; }
    public String getMemberName() { return memberName; }
    public LocalDateTime getStartTime() { return startTime; }
}
