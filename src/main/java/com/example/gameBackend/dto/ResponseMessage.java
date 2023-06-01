package com.example.gameBackend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMessage {

    private boolean won;
    private double bet;
    private int clientNumber;
    private double winnings;

    @JsonCreator
    public ResponseMessage(@JsonProperty("won") boolean won,
                           @JsonProperty("bet") double bet,
                           @JsonProperty("clientNumber") int clientNumber,
                           @JsonProperty("winnings") double winnings) {
        this.won = won;
        this.bet = bet;
        this.clientNumber = clientNumber;
        this.winnings = winnings;
    }

}
