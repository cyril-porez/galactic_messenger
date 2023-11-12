package com.example.galactic_messenger.Handlers;

//import lombok.extern.log4j.Log4j2;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Component
@MessageEndpoint

public class ServerSocketHandler {
    @ServiceActivator(inputChannel = "toTcp")
    public byte[] handleMessage(byte[] msg) {
        // implement some business logic here
        return msg;
    }
}