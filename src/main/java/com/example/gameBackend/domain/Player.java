package com.example.gameBackend.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Player {

    @Positive
    @Min(1)
    @Max(Integer.MAX_VALUE)
    private float bet;

    @Positive
    @Min(1)
    @Max(99)
    private int number;

}