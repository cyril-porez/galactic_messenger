package com.example.galacticMessengerClient;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;



@SpringBootApplication
public class GalacticMessengerClientApplication {


	public static void help() {
		System.out.println("Afin de vous aider à utiliser l'application voici la liste de toutes les comandes:");
		System.out.println("Inscription:");
		System.out.println("- /register \"nom_d'utilisateur\" \"mot_de_passe\"");
		System.out.println("Connexion:");
		System.out.println("- /login \"nom_d'utilisateur\" \"mot_de_passe\"");

	}

  public static void request(String name, String password, String addressIp, String command){
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders httpHeaders = new HttpHeaders();

    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("name", name);
    map.add("password", password);

    HttpEntity<MultiValueMap<String, String>> req = new HttpEntity<>(map, httpHeaders);
    String url = String.format("http://%s/api/user/%s",addressIp, command);
    restTemplate.postForObject(url, req, Void.class, map);
	};

	public static void main(String[] args) {

		SpringApplication.run(GalacticMessengerClientApplication.class, args);
		String addressIp = args[0];
		String port = args[1];
		String adressServer = addressIp + ":" + port;

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
		System.out.println(args[0]);

		Scanner scanner = new Scanner(System.in);
		System.out.println("Veuillez entrer votre commande!");
		
		boolean isRunning = true;

		while(isRunning) {
			String command = scanner.nextLine();
			String[] commandSplit = command.split(" ");
		  String choiceCommand = commandSplit[0];
			switch (choiceCommand) {
				case "/register":
					request(commandSplit[1], commandSplit[2], adressServer, commandSplit[0]);
					break;
				case "/login":
					request(commandSplit[1], commandSplit[2], adressServer, commandSplit[0]);
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
	
}
