package com.example.ptproject.trainer.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateTrainerRequest {

    @NotBlank(message = "이름은 필수입니다")
    private String name;

    @NotBlank(message = "전문 분야는 필수입니다")
    private String specialty;

    public String getName() { return name; }
    public String getSpecialty() { return specialty; }
    public void setName(String name) { this.name = name; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
}
