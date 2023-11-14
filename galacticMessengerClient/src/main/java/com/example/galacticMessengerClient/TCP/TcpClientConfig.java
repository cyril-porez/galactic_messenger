package com.example.galacticMessengerClient.TCP;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.tcp.TcpOutboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetClientConnectionFactory;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Configuration
@EnableIntegration
public class TcpClientConfig {
    @Value("${server.address")
    private String serverAddress;
    private int serverPort = 49155;

    public static final CustomSerializerDeserializer SERIALIZER = new CustomSerializerDeserializer();
    @Bean
    public AbstractClientConnectionFactory clientCF() {

        TcpNetClientConnectionFactory clientCf = new TcpNetClientConnectionFactory(serverAddress, serverPort);
        clientCf.setSerializer(SERIALIZER);
        clientCf.setDeserializer(SERIALIZER);
        clientCf.setSoTcpNoDelay(true);
        clientCf.setSoKeepAlive(true);
        // clientCf.setSingleUse(true);
        // final int soTimeout = 5000;
        // clientCf.setSoTimeout(soTimeout);
        return clientCf;
    }
    @Bean
    public TcpOutboundGateway tcpOutGate() {
        TcpOutboundGateway outGate = new TcpOutboundGateway();
        outGate.setConnectionFactory(clientCF());
        outGate.setReplyChannel(sendChannel());
        return outGate;
    }
    @Bean
    public MessageChannel sendChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel receiveChannel() {
        return new DirectChannel();
    }


    @Transformer(inputChannel = "receiveChannel", outputChannel = "sendChannel")
    public String handleReply(String reply) {

        System.out.println("Client a reçu la réponse : " + reply);

        // Logique métier côté client (ex. préparation d'un nouveau message)
        String newMessage = "Nouveau message du client";
        // Traitement de la réponse, si nécessaire

        return "Nouveau message à envoyer : " + newMessage;
    }

}
