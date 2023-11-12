package com.example.galactic_messenger.WebSocket_TCP_UDP;

//import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.Serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.nio.charset.StandardCharsets;

/**
 * A custom serializer for incoming and/or outcoming messages.
 */

public class CustomSerializerDeserializer implements Serializer<byte[]>, Deserializer<byte[]> {

    @Override
    public byte @NotNull [] deserialize(InputStream inputStream) throws IOException {
        byte[] message = new byte[0];
        if (inputStream.available() > 0) {
            message = inputStream.readAllBytes();
        }
        String msg = new String(message, StandardCharsets.UTF_8);
        System.out.println("Deserialized message : " + msg);
        return message;
    }

    @Override
    public void serialize(byte @NotNull [] message, OutputStream outputStream) throws IOException {
        String msg = new String(message, StandardCharsets.UTF_8);
        System.out.println("Serialize message : " + msg);
        /* log.info("Serializing {}", new String(message, StandardCharsets.UTF_8));*/
        outputStream.write(message);
        outputStream.flush();
    }
}