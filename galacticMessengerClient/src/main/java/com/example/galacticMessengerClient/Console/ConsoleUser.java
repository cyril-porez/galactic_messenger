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
        System.out.println("Bienvenue sur Galactic Messenger.\n");
        System.out.println("Inscrit toi ou connecte toi à l'application.\n");
        System.out.println("Inscription:");
        System.out.println("- /register \"nom_d'utilisateur\" \"mot_de_passe\"");
        System.out.println("Connexion:");
        System.out.println("- /login \"nom_d'utilisateur\" \"mot_de_passe\"");
        System.out.println("Demander de l'aide:");
        System.out.println("- /help\n");
    }
    public boolean loggedUserInstruction(String name){
        System.out.println("\n==================");
        System.out.println("GALACTIC MESSENGER");
        System.out.println("==================\n");
        System.out.printf("Bienvenue, %s dans le Galactic Messenger !\n", name);
        System.out.println("Entrer /help pour voir toutes les commandes disponible.\n");

        return false;
    }

    public static void help() {
        System.out.println("Afin de vous aider à utiliser l'application voici la liste de toutes les commandes:");
        System.out.println("Inscription:");
        System.out.println("- /register \"nom_d'utilisateur\" \"mot_de_passe\"");
        System.out.println("Connexion:");
        System.out.println("- /login \"nom_d'utilisateur\" \"mot_de_passe\"");
        System.out.println("Pour fermer le client: ");
        System.out.println("- /exit");

    }

    public static void help_forLoggedUser(){
        /* Commands liste utilisateurs connectés */
        System.out.println("Liste des commandes pour l'utilisateur connecté : ");
        System.out.println("Voir la liste des utilisateurs connectés");
        System.out.println("- /online_users \n");

        /* Commands messages one_to_one*/
        System.out.println("Demande de chat");
        System.out.println("- /private_chat \"utilisateur 2\"");
        System.out.println("Accepter ou refuser la connexion");
        System.out.println("- /accept \"utilisateur 1\"");
        System.out.println("- /decline \"utilisateur 1\"");
        System.out.println("Quitter le chat");
        System.out.println("- /exit_private_chat \n");

        /* Commandes messages group_chat */
        System.out.println("Créer un groupe chat");
        System.out.println("- /create_group \"nom du groupe\"");
        System.out.println("Rejoindre un groupe chat");
        System.out.println("- /join_group \"nom du groupe\"");
        System.out.println("Envoyer un message dans le groupe ");
        System.out.println("- /msg_group \"nom du groupe\" \"message\"");
        System.out.println("Quitter un groupe chat");
        System.out.println("- /exit_group \"nom du groupe\"\n");

        /* Commandes upload/download en UDP*/
        System.out.println("Upload un fichier");
        System.out.println("- /upload_file \"chemin_du_fichier\"");
        System.out.println("Download un fichier");
        System.out.println("- /download_file \"chemin_du_fichier\"");
        System.out.println("Lister les fichiers d'une conversation ou groupe");
        System.out.println("- /list_files \n");

        /* Commandes pour chat securisé dans un groupe*/
        System.out.println("Créer un groupe sécurisé");
        System.out.println("- /create_secured_group \"nom_du_group\" \"mot_de_passe\"");
        System.out.println("Rejoindre un groupe sécurisé");
        System.out.println("- /join_secured_group \"nom_du_group\" \"mot_de_passe\"\n");

        /* Deconnexion et quitter l'application */
        System.out.println("Pour se déconnecter");  /* Fonction non demandé dans le sujet*/
        System.out.println("- /logout ");
        System.out.println("Pour fermer le client: ");
        System.out.println("- /exit");

    }

    public void ConsoleUseGalacticMessenger() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez entrer votre commande!");

        boolean isRunning = true;
        ObjectMapper m = new ObjectMapper();
        JsonNode payloadNode = null;
        String sub = "";
        boolean once = true;

        while (isRunning) {
            if (!Session.isEmpty() && Session.getData("token") != null) {
                try {
                    payloadNode = m.readTree(decodeJWT(
                            (String) Session
                                    .getData("token"))
                            .get("payload")
                            .asText());
                    sub = payloadNode.get("sub").asText();
                    if (once) {
                        once = loggedUserInstruction(sub);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            System.out.printf("[ %s ] > ", sub == "" ? "Invité" : sub);

            String command = scanner.nextLine();
            String[] commandSplit = command.split(" ");
            String choiceCommand = commandSplit[0];

            if (Session.getData("token") == null) {
                commandsNotConnected(commandSplit, choiceCommand);
            } else {
                commandsConnected(commandSplit, choiceCommand);
            }

        }
        scanner.close();
    }


    public boolean commandsNotConnected(String[] commandSplit, String choiceCommand){
        //Commande disponibles pour l'invité
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
        return false;
    }
    public boolean commandsConnected(String[] commandSplit, String choiceCommand){
        //Commande disponibles pour l'invité
        switch (choiceCommand) {
            case "/help":
                help_forLoggedUser();
                break;
            case "/online_users":
                break;
            case "/private_chat":
                System.out.println("Commande indisponible");
                break;
            case "/accept":
                break;
            case "/decline":
                break;
            case "exit_private_chat":
                break;
            default:
                System.out.println("Commande non reconnue par le système !");
                break;
        }
        return true;
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
