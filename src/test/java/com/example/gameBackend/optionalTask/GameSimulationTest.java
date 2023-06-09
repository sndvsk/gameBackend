package com.example.gameBackend.optionalTask;

import com.example.gameBackend.domain.Player;
import com.example.gameBackend.dto.ResponseMessage;
import com.example.gameBackend.service.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameSimulationTest {

    private static final int NUMBER_OF_GAMES = 1_000_000;
    private static final int NUMBER_OF_THREADS = 24;

    private GameService gameService;

    @BeforeEach
    public void init() {
        gameService = new GameService();
    }

    @Test
    public void simulateGames() {
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        List<Future<ResponseMessage>> futures = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_GAMES; i++) {
            futures.add(executorService.submit(() -> {
                Player player = new Player();
                player.setBet(gameService.getRandomNumber()); // set bet randomly from 1 to 99
                player.setNumber(gameService.getRandomNumber()); // set number randomly from 1 to 99
                return gameService.playGame(player);
            }));
        }

        var games = futures.stream().map(future -> {
            try {
                return future.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());

        double totalBets = games.stream().mapToDouble(ResponseMessage::getBet).sum();
        double totalWins = games.stream().mapToDouble(ResponseMessage::getWinnings).sum();

        double rtp = (totalWins / totalBets) * 100;

        executorService.shutdown();

        try {
            boolean terminated = executorService.awaitTermination(60, TimeUnit.MINUTES);
            if (!terminated) {
                System.out.println("Some tasks are still running after the timeout. Force shutting down...");
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("RTP: " + rtp + "%");
    }



}
