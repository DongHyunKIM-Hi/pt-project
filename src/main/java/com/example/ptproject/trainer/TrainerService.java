package com.example.ptproject.trainer;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService {

    private final TrainerRepository trainerRepository;

    public TrainerService(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    public Trainer registerTrainer(String name, String specialty, String profileImageFileName) {
        return trainerRepository.save(name, specialty, profileImageFileName);
    }

    public Trainer getTrainer(long id) {
        Trainer trainer = trainerRepository.findById(id);
        if (trainer == null) {
            throw new TrainerNotFoundException(id);
        }
        return trainer;
    }

    public List<Trainer> getTrainers(String specialty) {
        List<Trainer> trainers = trainerRepository.findAll();
        if (specialty == null) {
            return trainers;
        }
        return trainers.stream()
                .filter(t -> t.getSpecialty().equals(specialty))
                .toList();
    }
}
