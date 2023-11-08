package com.example.galacticMessengerClient.Console;

// import org.yaml.snakeyaml.scanner.Scanner;
import java.util.Scanner;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.galacticMessengerClient.Request.RequestApi;

public class ConsoleUser {
	public ConsoleUser() {

	}

	public void displayLaunchInstruction() {
		System.out.println("==================");
		System.out.println("GALACTIC MESSENGER");
		System.out.println("==================");
		System.out.println("Bienvenue sur galactic Messenger.");
		System.out.println();
		System.out.println("Afin d'utiliser l'application, voici les commandes");
		System.out.println();
		System.out.println("Inscription:");
		System.out.println("- /register \"nom_d'utilisateur\" \"mot_de_passe\"");
		System.out.println("Connexion:");
		System.out.println("- /login \"nom_d'utilisateur\" \"mot_de_passe\"");
		System.out.println("Demander de l'aide:");
		System.out.println("- /help");
		System.out.println();
	}

	public static void help() {
		System.out.println("Afin de vous aider à utiliser l'application voici la liste de toutes les comandes:");
		System.out.println("Inscription:");
		System.out.println("- /register \"nom_d'utilisateur\" \"mot_de_passe\"");
		System.out.println("Connexion:");
		System.out.println("- /login \"nom_d'utilisateur\" \"mot_de_passe\"");

	}

	public void ConsoleUseGalacticMessenger(String[] args) {
		RequestApi requestApi = new RequestApi();
		String addressIp = args[1];
		String port = args[2];
        
        String adressServer = addressIp + ":" + port;
		Scanner scanner = new Scanner(System.in);

        displayLaunchInstruction();

		System.out.println("Veuillez entrer votre commande!");

		boolean isRunning = true;

		while (isRunning) {
			String command = scanner.nextLine();
			String[] commandSplit = command.split(" ");
			String choiceCommand = commandSplit[0];
			String username = commandSplit[1];
			String password = commandSplit[2];
			switch (choiceCommand) {
				case "/register":
					String hashedPassword = hashPassword(password);
					requestApi.request(username, hashedPassword, adressServer, choiceCommand);
					break;
				case "/login":
					requestApi.request(username, password, adressServer, choiceCommand);
					// request(commandSplit[1], commandSplit[2], adressServer, commandSplit[0]);
					break;
				case "/help":
					help();
					break;
				default:
					System.out.println("Commande non reconnus par le système !");
					break;
			}
		}

		scanner.close();
	}

	public String hashPassword(String password) {
		return new BCryptPasswordEncoder().encode(password);
	}

}
