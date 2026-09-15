package com.example.ptproject.reservation;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(long id) {
        super("해당 예약을 찾을 수 없습니다. id=" + id);
    }
}
