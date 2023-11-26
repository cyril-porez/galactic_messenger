package com.example.galacticMessengerClient.Services;

import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import java.lang.reflect.Type;

public class MyStompSessionHandler extends StompSessionHandlerAdapter{
   @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println("Connecté au WebSocket server");

        // Exemple de souscription à un topic
        session.subscribe("/topic/someTopic", this);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        // Retourne le type de payload attendu, par exemple String.class
        return String.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        // Traite le message reçu
        String message = (String) payload;
        System.out.println("Message reçu: " + message);
    }
}
