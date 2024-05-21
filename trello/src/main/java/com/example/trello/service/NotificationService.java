package com.example.trello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void notifyCardUpdate(String message, Long cardId) {
        // Notify users subscribed to this card's topic
        messagingTemplate.convertAndSend("/topic/card/" + cardId, message);
    }
}
