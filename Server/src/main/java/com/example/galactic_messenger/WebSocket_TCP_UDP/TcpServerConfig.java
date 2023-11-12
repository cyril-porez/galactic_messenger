package com.example.galactic_messenger.WebSocket_TCP_UDP;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;

@Configuration
@EnableIntegration
@IntegrationComponentScan
public class TcpServerConfig {
    public static final CustomSerializerDeserializer SERIALIZER = new CustomSerializerDeserializer();
    // @Value("${server.port}")
    private int serverPort = 49155;

    //@Value("${server.address")
    //private String serverAddress;

    @MessagingGateway(defaultRequestChannel = "toTcp")
    public interface Gateway {
        void send(String message, @Header(IpHeaders.CONNECTION_ID) String connectionId);
    }

    @Bean
    public AbstractServerConnectionFactory serverCF() { // Création du server factory
        TcpNetServerConnectionFactory serverCf = new TcpNetServerConnectionFactory(serverPort);
        serverCf.setSerializer(SERIALIZER);
        serverCf.setDeserializer(SERIALIZER);
        serverCf.setSoTcpNoDelay(true);
        serverCf.setSoKeepAlive(true);
        // serverCf.setSingleUse(true);
        // final int soTimeout = 5000;
        // serverCf.setSoTimeout(soTimeout);
        return serverCf;
    }

    @Bean
    public TcpInboundGateway tcpInGate() {
        TcpInboundGateway inGate = new TcpInboundGateway();
        inGate.setConnectionFactory(serverCF());
        inGate.setRequestChannel(requestChannel());
        // inGate.setReplyChannel(toTcp());
        return inGate;
    }
    @Bean
    public MessageChannel requestChannel() { return new DirectChannel();}

    @ServiceActivator(inputChannel = "requestChannel")
    public String handleMessage(String message) {
        System.out.println("Serveur a reçu le message : " + message);

        // Logique métier (ex. transformation du message)
        String response = "Réponse du serveur : " + message.toUpperCase();

        return "Réponse : " + response;
    }

    /*
    @Bean
    public MessageChannel toTcp() {
        return new DirectChannel();
    }
    */




    // Ne fonctionne pas
    /*@Bean
    public ThreadAffinityClientConnectionFactory ClientInThread() {
        return new ThreadAffinityClientConnectionFactory(clientCF());
    }*/

    // testing function
    /* public void setConnectionTest(@Nullable Predicate<TcpConnectionSupport> connectionTest) {
        this.connectionTest = connectionTest;
    }*/

}
