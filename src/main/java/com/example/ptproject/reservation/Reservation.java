package com.example.ptproject.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    private long id;
    private long trainerId;
    private String memberName;
    private LocalDateTime startTime;
}
