package com.example.ptproject.trainer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TrainerRepository {

    private final Map<Long, Trainer> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Trainer save(String name, String specialty, String profileImageFileName) {
        long id = idGenerator.getAndIncrement();
        Trainer trainer = new Trainer(id, name, specialty, profileImageFileName);
        store.put(id, trainer);
        return trainer;
    }

    public Trainer findById(long id) {
        return store.get(id);
    }

    public List<Trainer> findAll() {
        return new ArrayList<>(store.values());
    }
}
