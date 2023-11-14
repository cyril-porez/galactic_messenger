package com.example.galacticMessengerClient.Console;


import java.util.Objects;
import java.util.Base64;
import java.util.Scanner;

import com.example.galacticMessengerClient.TCP.TcpClientConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.example.galacticMessengerClient.Session;
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
        System.out.println("Pour fermer le client: ");
        System.out.println("- /exit");
    }

    public void ConsoleUseGalacticMessenger() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez entrer votre commande!");

        boolean isRunning = true;
        ObjectMapper m = new ObjectMapper();
        JsonNode payloadNode = null;
        String username = "";
        boolean sessionHasToken;

        while (isRunning) {
            sessionHasToken = !Session.isEmpty() && Session.getData("token") != null;
            if(sessionHasToken) {
                try {
                    payloadNode = m.readTree(decodeJWT(
                        (String)Session
                        .getData("token"))
                        .get("payload")
                        .asText());
                    username = payloadNode.get("sub").asText();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } 
            else {
                username = "";
            }

            System.out.printf(
                "[ %s ] > ", 
                username == "" ? "Invité" : username
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
                case "/private_chat":
                    // Vérification de connexion
                    if(!sessionHasToken) {
                        System.out.println("Commande indisponible. Veuillez vous connecter.");
                        break;
                    }
                    handlePrivateChat(commandSplit, choiceCommand);
                    break;
                case "/logout":
                    // Vérification de connexion
                    if(!sessionHasToken) {
                        System.out.println("Commande indisponible. Veuillez vous connecter.");
                        break;
                    }
                    handleLogout(commandSplit, choiceCommand);
                    // System.out.println("Vous vous êtes déconnecté avec succès.");
                    break;
                /*
                case "/accept":
                    handleAccept(commandSplit, choiceCommand);
                    break;*/
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
                    String token = node.has("token") ? node.get("token").asText() : "Token inconnu";

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

    public void handleLogout(String[] commands, String choiceCommand) {
        try {
            if(commands.length == 1) {
                ObjectMapper m = new ObjectMapper();
                String username = m.readTree(decodeJWT(
                        (String)Session
                        .getData("token"))
                        .get("payload")
                        .asText()).get("sub").asText();
                ApiResponse res = requestApi.requestLogout(username, choiceCommand, adressServer);

                if(res.getStatus() == 200) {
                    Session.deleteData("token");
                }
                else {
                    System.out.println("Erreur: La déconnexion à échouée");
                    return;
                }

                System.out.println(res.getMessage());
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void handlePrivateChat(String []commands, String choiceCommand){
        if (commands.length == 3 && Objects.equals(commands[0], "azerty")){
            ApiResponse res = requestApi.requestConnection(commands[0], commands[1], choiceCommand, adressServer);
            System.out.println(res.getMessage());
            /*
                Logique a implementer ici
             */
            TcpClientConfig client1 = new TcpClientConfig();
            client1.handleReply("Vous et");
        }
    }

    public void handleAccept(String [] commands, String choiceCommand){
        if (commands.length == 2 && Objects.equals(commands[1], "azerty")){

            TcpClientConfig clientConfig = new TcpClientConfig();
            clientConfig.handleReply("Vous et");
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
