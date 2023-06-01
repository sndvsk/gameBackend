package com.example.gameBackend.controller;

import com.example.gameBackend.domain.Player;
import com.example.gameBackend.dto.ResponseMessage;
import com.example.gameBackend.service.GameService;
import jakarta.validation.Valid;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

@Controller
@Validated
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @MessageMapping("/play")
    @SendTo("/topic/winnings")
    public ResponseMessage playGame(@Valid Player player) {
        return gameService.playGame(player);
    }
}
