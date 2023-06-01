package com.example.gameBackend.config;

import com.example.gameBackend.domain.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class WebSocketProxy {

    private final SimpMessagingTemplate messagingTemplate;

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    public WebSocketProxy(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendMessage(String destination, String payload){
        messagingTemplate.convertAndSend(destination, payload);
        log.info("Message " + payload + " is sent to: " + destination);
    }

}
