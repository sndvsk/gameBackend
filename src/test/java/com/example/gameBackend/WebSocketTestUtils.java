package com.example.gameBackend;

import com.example.gameBackend.dto.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class WebSocketTestUtils {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public WebSocketStompClient createWebSocketClient() {
        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        return stompClient;
    }

    private List<Transport> createTransportClient() {
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        return transports;
    }

    public static class MyStompSessionHandler extends StompSessionHandlerAdapter {
        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            log.info("Stomp client is connected");
            super.afterConnected(session, connectedHeaders);
        }

        @Override
        public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
            log.info("Exception: " + exception);
            super.handleException(session, command, headers, payload, exception);
        }
    }

    public static class MyStompFrameHandler implements StompFrameHandler {

        private final Consumer<ResponseMessage> frameHandler;

        public MyStompFrameHandler(Consumer<ResponseMessage> frameHandler) {
            this.frameHandler = frameHandler;
        }

        @Override
        public Type getPayloadType(StompHeaders headers) {
            return ResponseMessage.class;
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            log.info("received message: {} with headers: {}", payload, headers);
            frameHandler.accept((ResponseMessage) payload);
        }
    }
}