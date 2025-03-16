package com.example.ChatApp.controller;

import com.example.ChatApp.modal.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class RealTimeChat {

    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendTo("group/public")
    public Message receiveMessage(@Payload Message message) {
        // Correct method call: convertAndSend()
        simpMessagingTemplate.convertAndSend("/group"+ message.getChat().getId().toString(),message);

        return message;
    }
}
