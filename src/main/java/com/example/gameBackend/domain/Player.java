package com.example.gameBackend.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

//@AllArgsConstructor
public class Player {

    @Positive
    @Min(1)
    @Max(100)
    private float bet;

    @Positive
    @Min(1)
    @Max(100)
    private int number;

    public Player(float bet, int number) {
        this.bet = bet;
        this.number = number;
    }

/*    @JsonCreator
    public Player(@JsonProperty("bet") float bet, @JsonProperty("number") int number) {
        this.bet = bet;
        this.number = number;
    }*/

    public Player() {
    }

    // Getters and Setters

    public float getBet() {
        return bet;
    }

    public void setBet(float bet) {
        this.bet = bet;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}