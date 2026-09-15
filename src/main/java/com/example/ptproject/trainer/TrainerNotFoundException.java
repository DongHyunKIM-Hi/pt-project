package com.example.ptproject.trainer;

public class TrainerNotFoundException extends RuntimeException {
    public TrainerNotFoundException(long id) {
        super("해당 트레이너를 찾을 수 없습니다. id=" + id);
    }
}
