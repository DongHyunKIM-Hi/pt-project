package com.example.ptproject.reservation;

import com.example.ptproject.trainer.TrainerService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TrainerService trainerService;

    public ReservationService(ReservationRepository reservationRepository, TrainerService trainerService) {
        this.reservationRepository = reservationRepository;
        this.trainerService = trainerService;
    }

    public Reservation createReservation(long trainerId, String memberName, LocalDateTime startTime) {
        trainerService.getTrainer(trainerId);

        if (reservationRepository.existsConflict(trainerId, startTime, null)) {
            throw new ReservationConflictException(trainerId, startTime);
        }

        return reservationRepository.save(trainerId, memberName, startTime);
    }

    public Reservation getReservation(long id) {
        Reservation reservation = reservationRepository.findById(id);
        if (reservation == null) {
            throw new ReservationNotFoundException(id);
        }
        return reservation;
    }

    public List<Reservation> getReservations(Long trainerId) {
        List<Reservation> reservations = reservationRepository.findAll();
        if (trainerId == null) {
            return reservations;
        }
        return reservations.stream()
                .filter(r -> r.getTrainerId() == trainerId)
                .toList();
    }

    public Reservation updateReservation(long id, LocalDateTime newStartTime) {
        Reservation existing = getReservation(id);

        if (reservationRepository.existsConflict(existing.getTrainerId(), newStartTime, id)) {
            throw new ReservationConflictException(existing.getTrainerId(), newStartTime);
        }

        return reservationRepository.update(id, newStartTime);
    }

    public void deleteReservation(long id) {
        reservationRepository.deleteById(id);
    }
}
