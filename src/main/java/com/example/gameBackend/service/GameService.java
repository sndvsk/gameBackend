package com.example.gameBackend.service;

import com.example.gameBackend.domain.Player;
import com.example.gameBackend.dto.ResponseMessage;
import com.example.gameBackend.exception.CustomBadRequestException;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class GameService {

    private static final float lost = 0.0f;


    private void validateInputs(int clientNumber, double bet) {
        if (clientNumber < 1 || clientNumber > 99) {
            throw new CustomBadRequestException(String.format("Invalid number: %s", clientNumber));
        }
        if (bet < 1 || bet > Integer.MAX_VALUE) {
            throw new CustomBadRequestException(String.format("Invalid bet: %s", bet));
        }
    }

    public ResponseMessage playGame(Player player) {
        int clientNumber = player.getNumber();
        double bet = player.getBet();

        validateInputs(clientNumber, bet);

        int serverNumber = getRandomNumber();
        float winnings = calculateWinnings(player.getNumber(), serverNumber, player.getBet());

        return new ResponseMessage(winnings > bet, bet, clientNumber, winnings);
    }

    public int getRandomNumber() {
        int number = 0;
        do {
            number = (int) Math.abs(ThreadLocalRandom.current().nextGaussian() * 100.0);
        } while (number >= 100 || number < 1); // if 100 or more, then it crashes the bet * (99.0f / (100 - clientNumber)) formula
        return number;
    }

    private float calculateWinnings(int clientNumber, int serverNumber, float bet) {
        if (clientNumber > serverNumber)
            return bet * (99.0f / (100 - clientNumber)); // won
/*        else if (clientNumber == serverNumber)
            return bet; // tie */
        else
            return lost; // lost
    }

}


