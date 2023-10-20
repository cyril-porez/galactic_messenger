package com.example.galactic_messenger;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.example.galactic_messenger.Services.ServerCommand;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.galactic_messenger")
public class GalacticMessengerApplication {

	public static void main(String[] args) {
		int port = 8082;
		// SpringApplication.run(GalacticMessengerApplication.class, args);
		SpringApplication app = new SpringApplication(GalacticMessengerApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", port));
		app.run(args); 
		try {
			String adressIp = InetAddress.getLocalHost().getHostAddress();
			System.out.printf("\nServer available at %s:%d\n" , adressIp, port);
		} catch(UnknownHostException e) {
			e.printStackTrace();
		}
		
		System.out.println("Test");	
		ServerCommand serverCommand = new ServerCommand();
		serverCommand.Command();
	}

}
