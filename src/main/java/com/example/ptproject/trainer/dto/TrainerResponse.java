package com.example.ptproject.trainer.dto;

import com.example.ptproject.trainer.Trainer;

public class TrainerResponse {

    private final long id;
    private final String name;
    private final String specialty;
    private final String profileImageUrl;

    private TrainerResponse(long id, String name, String specialty, String profileImageUrl) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.profileImageUrl = profileImageUrl;
    }

    public static TrainerResponse from(Trainer trainer) {
        String imageUrl = trainer.getProfileImageFileName() == null
                ? null
                : "/v1/trainers/" + trainer.getId() + "/profile-image";
        return new TrainerResponse(trainer.getId(), trainer.getName(), trainer.getSpecialty(), imageUrl);
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getSpecialty() { return specialty; }
    public String getProfileImageUrl() { return profileImageUrl; }
}
