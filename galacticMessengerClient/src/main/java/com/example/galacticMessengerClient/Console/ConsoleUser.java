package com.example.galacticMessengerClient.Console;


// import org.yaml.snakeyaml.scanner.Scanner;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.galacticMessengerClient.Request.RequestApi;
import com.example.galacticMessengerClient.controllers.ApiResponse;
import com.fasterxml.jackson.databind.JsonNode;

public class ConsoleUser {

    private RequestApi requestApi;
    String addressIp;
    String port;
    private String adressServer;

    public ConsoleUser(String[] args) {
        requestApi = new RequestApi();
        addressIp = args[0];
        port = args[1];
        adressServer = addressIp + ":" + port;
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

    public void ConsoleUseGalacticMessenger() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez entrer votre commande!");

        boolean isRunning = true;

        while (isRunning) {
            String command = scanner.nextLine();
            String[] commandSplit = command.split(" ");
            String choiceCommand = commandSplit[0];

            switch (choiceCommand) {
                case "/register":
                    handleRegister(commandSplit, choiceCommand);
                    break;
                case "/login":
                    System.out.println("/login");
                    handleLogin(commandSplit, choiceCommand);
                    break;
                case "/help":
                    help();
                    break;
                case "/exit":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Commande non reconnus par le système !");
                    break;
            }
        }

        scanner.close();
    }

    public void handleRegister(String[] commands, String choiceCommand) {
        if(commands.length == 3) {
            ApiResponse res = requestApi.request(commands[1], commands[2], adressServer, choiceCommand);
            System.out.println(res.getMessage());

        }
    }

    public void handleLogin(String[] commands, String choiceCommand) {
        if(commands.length == 3) {
            System.out.println("test");
            ApiResponse res = requestApi.request(commands[1], commands[2], adressServer, choiceCommand);
           
            ObjectMapper mapper = new ObjectMapper();
            String jsonData;
            try {
                jsonData = mapper.writeValueAsString(res.getData());
            } catch (JsonProcessingException e) {
                jsonData = "{}"; 
            }
             
            try {
                JsonNode outerNode = mapper.readTree(jsonData);
                JsonNode node = mapper.readTree(outerNode.asText());
                String name = node.has("name") ? node.get("name").asText() : "Nom inconnu";
                int id = node.has("id") ? node.get("id").asInt() : -1;
                System.out.println("Id => " + id);
                System.out.println("Name => " + name);
            } catch (JsonProcessingException e) {
                e.printStackTrace(); 
            }
            
            System.out.println(res.getMessage());
           
        }
        else {
            System.out.println("La commande est incorrecte. Entrez '/help' pour voir les différentes commandes.\n");
        }
    }
}
