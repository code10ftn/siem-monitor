package com.code10.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final SimpMessagingTemplate template;

    @Autowired
    public NotificationService(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void sendNotification(String message) {
        template.convertAndSend("/notifications", message);
    }
}
