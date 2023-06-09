package com.example.gameBackend.service;

import com.example.gameBackend.domain.Player;
import com.example.gameBackend.dto.ResponseMessage;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @InjectMocks
    private GameService gameService;

    @Mock
    private Player player;

    @Rule //initMocks
    public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void testPlayGame_validInputs_1() {
        when(player.getNumber()).thenReturn(50);
        when(player.getBet()).thenReturn(40.5f);

        ResponseMessage response = gameService.playGame(player);

        if (response.isWon()) { // if because the outcome of the game is decided by random
            assertTrue(response.isWon());
            assertEquals(80.19, response.getWinnings(), 0.01);
        } else {
            assertFalse(response.isWon());
            assertEquals(0, response.getWinnings(), 0.01);
        }
    }

    @Test
    public void testPlayGame_validInputs_2() {
        when(player.getNumber()).thenReturn(99);
        when(player.getBet()).thenReturn(99f);

        ResponseMessage response = gameService.playGame(player);

        if (response.isWon()) { // if because the outcome of the game is decided by random
            assertTrue(response.isWon());
            assertEquals(9801, response.getWinnings(), 0.01);
        } else {
            assertFalse(response.isWon());
            assertEquals(0, response.getWinnings(), 0.01);
        }
    }

}
