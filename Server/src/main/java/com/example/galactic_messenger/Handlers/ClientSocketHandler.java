package com.example.galactic_messenger.Handlers;

//import lombok.extern.log4j.Log4j2;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;


@Component
@MessageEndpoint

public class ClientSocketHandler {
    @ServiceActivator(inputChannel = "toTcp")
    public byte[] handleMessage(byte[] msg) {
        // TODO implement some buisiness logic here
        return msg;
    }
}
