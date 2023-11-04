package com.example.galacticMessengerClient;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.galacticMessengerClient.Console.ConsoleUser;


@SpringBootApplication
public class GalacticMessengerClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(GalacticMessengerClientApplication.class, args);
		ConsoleUser consoleUser = new ConsoleUser();
		consoleUser.displayLaunchInstruction();
		System.out.println(args[0]);
		consoleUser.ConsoleUseGalacticMessenger(args);
	}	
}
