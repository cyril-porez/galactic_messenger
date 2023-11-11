package com.example.galactic_messenger.WebSocket_TCP_UDP;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.TcpOutboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.context.request.RequestContextListener;

@Configuration
@EnableIntegration
@IntegrationComponentScan
public class ServerSocket {
    public static final CustomSerializerDeserializer SERIALIZER = new CustomSerializerDeserializer();
    // @Value("${server.port}")
    private int serverPort = 49154;
    @Value("${server.address")
    private String serverAddress;

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
    /*@Bean
    public ThreadAffinityClientConnectionFactory ClientInThread() {
        return new ThreadAffinityClientConnectionFactory(clientCF());
    }*/

    // testing function
    /* public void setConnectionTest(@Nullable Predicate<TcpConnectionSupport> connectionTest) {
        this.connectionTest = connectionTest;
    }*/

    @Bean
    public TcpInboundGateway tcpInGate() {
        TcpInboundGateway inGate = new TcpInboundGateway();
        inGate.setConnectionFactory(serverCF());
        inGate.setRequestChannel(fromTcp());
        inGate.setReplyChannel(toTcp());
        return inGate;
    }

    @Bean
    public TcpOutboundGateway tcpOutGate() {
        TcpOutboundGateway outGate = new TcpOutboundGateway();
        outGate.setConnectionFactory(clientCF());
        outGate.setReplyChannel(toTcp());
        return outGate;
    }

    @Bean
    public MessageChannel fromTcp() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel toTcp() {
        return new DirectChannel();
    }

}
