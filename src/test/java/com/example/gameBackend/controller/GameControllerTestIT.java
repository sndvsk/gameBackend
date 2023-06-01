package com.example.gameBackend.controller;

import com.example.gameBackend.WebSocketTestUtils;
import com.example.gameBackend.config.WebSocketProxy;
import com.example.gameBackend.domain.Player;
import com.example.gameBackend.dto.ResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

/** @noinspection SpringJavaInjectionPointsAutowiringInspection*/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class GameControllerTestIT {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private WebSocketProxy wsProxy;

    @Autowired
    private ObjectMapper objectMapper;

    private WebSocketStompClient stompClient;
    private StompSession stompSession;
    private final WebSocketTestUtils webSocketTestUtils = new WebSocketTestUtils();

    private static final String SEND_PLAY_ENDPOINT = "/app/play";
    private static final String SUBSCRIBE_TOPIC_ENDPOINT = "/topic/winnings";

    @BeforeEach
    public void setUp() throws Exception {
        //BlockingQueue<String> blockingQueue = new LinkedBlockingDeque<>();
        String wsUrl = "ws://localhost:" + port + "/websocket";
        stompClient = webSocketTestUtils.createWebSocketClient();
        stompSession = stompClient.connect(wsUrl, new WebSocketTestUtils.MyStompSessionHandler()).get();
    }

    @AfterEach
    public void tearDown() {
        stompSession.disconnect();
        stompClient.stop();
    }

    @Test
    public void connectsToSocket() {
        assertThat(stompSession.isConnected()).isTrue();
    }

    @Test
    public void testPlayGame() throws Exception {

        Player player = new Player(40.5f, 50);
        String playerJson = objectMapper.writeValueAsString(player);
        //String message = "{\"bet\":\"40.5\",\"number\":\"50\"}";

        //given
        CompletableFuture<ResponseMessage> resultKeeper = new CompletableFuture<>();

        stompSession.subscribe(
                SUBSCRIBE_TOPIC_ENDPOINT,
                new WebSocketTestUtils.MyStompFrameHandler(resultKeeper::complete));

        //when
        wsProxy.sendMessage(SEND_PLAY_ENDPOINT, playerJson);
        //resultKeeper.get(5, TimeUnit.SECONDS);

        //then
        //assertNotNull(resultKeeper);
        //assertTrue(resultKeeper.get().isWon());
        //assertEquals(80.19, resultKeeper.get().getWinnings(), 0.01);
    }

}
