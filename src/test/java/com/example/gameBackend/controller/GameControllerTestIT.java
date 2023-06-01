package com.example.gameBackend.controller;

import com.example.gameBackend.utils.WebSocketTestUtils;
import com.example.gameBackend.domain.Player;
import com.example.gameBackend.dto.ResponseMessage;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class GameControllerTestIT {

    @Value("${local.server.port}")
    private int port;

    private WebSocketStompClient stompClient;
    private StompSession stompSession;
    private WebSocketTestUtils.MyStompSessionHandler sessionHandler;
    private final WebSocketTestUtils webSocketTestUtils = new WebSocketTestUtils();
    private final ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();

    private static final String SEND_PLAY_ENDPOINT = "/app/play";
    private static final String SUBSCRIBE_TOPIC_ENDPOINT = "/topic/winnings";

    @BeforeEach
    public void setUp() throws Exception {
        taskScheduler.afterPropertiesSet();

        String wsUrl = "ws://localhost:" + port + "/websocket";
        stompClient = webSocketTestUtils.createWebSocketClient();
        stompClient.setTaskScheduler(taskScheduler);
        sessionHandler = new WebSocketTestUtils.MyStompSessionHandler();
        stompSession = stompClient.connect(wsUrl, sessionHandler).get();
    }

    @After
    public void tearDown() {
        stompSession.disconnect();
        stompClient.stop();
    }

    @Test
    public void connectsToSocket() {
        assertThat(stompSession.isConnected()).isTrue();
    }

    @Test
    public void testPlayGame_1() throws Exception {

        // payload
        Player player = new Player(40.5f, 50);

        //given
        CompletableFuture<ResponseMessage> resultKeeper = new CompletableFuture<>();

        assertThat(stompSession.isConnected()).isTrue();

        var sub = stompSession.subscribe(
                SUBSCRIBE_TOPIC_ENDPOINT,
                new WebSocketTestUtils.MyStompFrameHandler(resultKeeper::complete));

        //when
        stompSession.send(SEND_PLAY_ENDPOINT, player);
        var gameResult = resultKeeper.get(10, TimeUnit.SECONDS);

        //then
        assertNotNull(gameResult);
        if (gameResult.isWon()) {
            assertTrue(gameResult.isWon()); // same as one the row up but anyway
            assertEquals(80.19, gameResult.getWinnings(), 0.01);
        } else {
            assertFalse(gameResult.isWon());
            assertEquals(0, gameResult.getWinnings(), 0.01);
        }
    }

}
