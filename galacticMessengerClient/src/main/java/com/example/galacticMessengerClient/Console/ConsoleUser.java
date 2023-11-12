package com.example.galacticMessengerClient.Console;


<<<<<<< HEAD
=======
import java.util.Base64;
// import org.yaml.snakeyaml.scanner.Scanner;
>>>>>>> main
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.example.galacticMessengerClient.Session;
import com.example.galacticMessengerClient.Request.RequestApi;
import com.example.galacticMessengerClient.controllers.ApiResponse;
import com.fasterxml.jackson.databind.JsonMappingException;
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
        ObjectMapper m = new ObjectMapper();
        JsonNode payloadNode = null;
        String sub = "";

        while (isRunning) {
            if(!Session.isEmpty() && Session.getData("token") != null) {
                try {
                    payloadNode = m.readTree(decodeJWT(
                        (String)Session
                        .getData("token"))
                        .get("payload")
                        .asText());
                    sub = payloadNode.get("sub").asText();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            System.out.printf(
                "[ %s ] > ", 
                sub == "" ? "Invité" : sub
            );
            String command = scanner.nextLine();
            String[] commandSplit = command.split(" ");
            String choiceCommand = commandSplit[0];


            switch (choiceCommand) {
                case "/register":
                    handleRegister(commandSplit, choiceCommand);
                    break;
                case "/login":
                    handleLogin(commandSplit, choiceCommand);
                    break;
                case "/help":
                    help();
                    break;
                case "/exit":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Commande non reconnue par le système !");
                    break;
            }
        }

        scanner.close();
    }

    public void handleRegister(String[] commands, String choiceCommand) {
        try{
            if(commands.length == 3) {
                ApiResponse res = requestApi.request(commands[1], commands[2], adressServer, choiceCommand);
                System.out.println(res.getMessage());

            }
        } catch (Exception e){
            String exception = e.getMessage().substring(7);
            String errorMessage = "";
            try{
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(exception);
                errorMessage = jsonNode.get("message").asText();
            } catch (JsonProcessingException jsonProcessingException){
                e.printStackTrace();
            }
            System.out.println(errorMessage);
        }
    }

    public void handleLogin(String[] commands, String choiceCommand) {
        try{
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
                    String token = node.has("token") ? node.get("token").asText() : "Token inconnu";
                    System.out.println("Id => " + id);
                    System.out.println("Name => " + name);
                    System.out.println("token =>" + token);

                    Session.setData("token", token);
                    
                } catch (JsonProcessingException e) {
                    e.printStackTrace(); 
                }
                
                System.out.println(res.getMessage());
            }
            else {
                System.out.println("La commande est incorrecte. Entrez '/help' pour voir les différentes commandes.\n");
            }
        } catch (Exception e){
            String exception = e.getMessage().substring(7);
            String errorMessage = "";
            try{
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(exception);
                errorMessage = jsonNode.get("message").asText();
            } catch (JsonProcessingException jsonProcessingException){
                e.printStackTrace();
            }
            System.out.println(errorMessage);
        }
    }

    private JsonNode decodeJWT(String token) {
        String[] chunks = token.split("\\.");

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonNode = mapper.createObjectNode();

        String payload = new String(Base64.getUrlDecoder().decode(chunks[1]));

        jsonNode.put("payload", payload);
        
        // System.out.println(jsonNode);
        return jsonNode;
    }
}
