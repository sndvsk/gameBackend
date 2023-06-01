package com.example.gameBackend.service;

import com.example.gameBackend.domain.Player;
import com.example.gameBackend.dto.ResponseMessage;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class GameService {

/*    public static class RandomNumber {
        int cycles;
        int number;

        public RandomNumber(int cycles, int number) {
            this.cycles = cycles;
            this.number = number;
        }

    }*/

    private static final float lost = 0.0f;

    public ResponseMessage playGame(Player player) {
        int clientNumber = player.getNumber();
        double bet = player.getBet();

        var serverNumber = getRandomNumber();
        float winnings = calculateWinnings(player.getNumber(), serverNumber, player.getBet());

        return new ResponseMessage(winnings > bet, bet, clientNumber, winnings, serverNumber);
    }

/*    public RandomNumber getRandomNumber() {
        int number = 0;
        int cycles = 0;
        do {
            number = (int) Math.abs(ThreadLocalRandom.current().nextGaussian() * 100.0);
            cycles++;
        } while (number >= 100);
        return new RandomNumber(cycles, number);
    }*/

    public int getRandomNumber() {
        int number = 0;
        do {
            number = (int) Math.abs(ThreadLocalRandom.current().nextGaussian() * 100.0);
        } while (number >= 100);
        return number;
    }

    private float calculateWinnings(int clientNumber, int serverNumber, float bet) {
        if (clientNumber > serverNumber)
            return bet * (99.0f / (100 - clientNumber));
/*        else if (clientNumber == serverNumber)
            return bet;*/
        else
            return lost;

    }


}


