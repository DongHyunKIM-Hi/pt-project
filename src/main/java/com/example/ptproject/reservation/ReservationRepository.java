package com.example.ptproject.reservation;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationRepository {

    private final Map<Long, Reservation> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Reservation save(long trainerId, String memberName, LocalDateTime startTime) {
        long id = idGenerator.getAndIncrement();
        Reservation reservation = new Reservation(id, trainerId, memberName, startTime);
        store.put(id, reservation);
        return reservation;
    }

    public Reservation findById(long id) {
        return store.get(id);
    }

    public List<Reservation> findAll() {
        return new ArrayList<>(store.values());
    }

    public boolean existsConflict(long trainerId, LocalDateTime startTime, Long excludeId) {
        return store.values().stream()
                .anyMatch(r -> r.getTrainerId() == trainerId
                        && r.getStartTime().equals(startTime)
                        && (excludeId == null || r.getId() != excludeId));
    }

    public Reservation update(long id, LocalDateTime startTime) {
        Reservation existing = store.get(id);
        if (existing == null) {
            return null;
        }
        Reservation updated = new Reservation(id, existing.getTrainerId(), existing.getMemberName(), startTime);
        store.put(id, updated);
        return updated;
    }

    public void deleteById(long id) {
        store.remove(id);
    }
}
