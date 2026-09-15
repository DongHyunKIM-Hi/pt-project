package com.example.ptproject.trainer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Trainer {
    private long id;
    private String name;
    private String specialty;
    private String profileImageFileName;
}
