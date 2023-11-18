package com.example.galacticMessengerClient.Commands;

import java.util.Base64;

import com.example.galacticMessengerClient.Session;
import com.example.galacticMessengerClient.Request.RequestApi;
import com.example.galacticMessengerClient.controllers.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HandleUsers {

    String addressIp;
    String port;
    private String adressServer;
    private RequestApi requestApi;

    public HandleUsers(String[] args){
        requestApi = new RequestApi();
        addressIp = args[0];
        port = args[1];
        adressServer = addressIp + ":" + port;
    }

    public void handleRegister(String[] commands, String choiceCommand) {
        try {
            if (commands.length == 3) {
                ApiResponse res = requestApi.request(commands[1], commands[2], adressServer, choiceCommand);
                System.out.println(res.getMessage());

            }
        } catch (Exception e) {
            String exception = e.getMessage().substring(7);
            String errorMessage = "";
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(exception);
                errorMessage = jsonNode.get("message").asText();
            } catch (JsonProcessingException jsonProcessingException) {
                e.printStackTrace();
            }
            System.out.println(errorMessage);
        }
    }

    public void handleLogin(String[] commands, String choiceCommand) {
        try {
            if (commands.length == 3) {
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
            } else {
                System.out.println("La commande est incorrecte. Entrez '/help' pour voir les différentes commandes.\n");
            }
        } catch (Exception e) {
            String exception = e.getMessage().substring(7);
            String errorMessage = "";
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(exception);
                errorMessage = jsonNode.get("message").asText();
            } catch (JsonProcessingException jsonProcessingException) {
                e.printStackTrace();
            }
            System.out.println(errorMessage);
        }
    }

    public void handleLogout(String[] commands, String choiceCommand) {
        try {
            if (commands.length == 1) {
                String username = getDataFromJWT((String) Session.getData("token"), "sub");
                ApiResponse res = requestApi.requestLogout(username, choiceCommand, adressServer);

                if (res.getStatus() == 200) {
                    Session.deleteData("token");
                } else {
                    System.out.println("Erreur: La déconnexion à échouée");
                    return;
                }

                System.out.println(res.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDataFromJWT(String jwt, String field) {
        // Divise le JWT
        String[] parts = jwt.split("\\.");
        if (parts.length != 3) {
            System.out.println("Jeton JWT invalide");
            return null;
        }

        ObjectMapper m = new ObjectMapper();

        // Analyse les deux premières parties du JWT
        for (int i = 0; i < 2; i++) {
            byte[] partBytes = Base64.getUrlDecoder().decode(parts[i]);
            String part = new String(partBytes);

            JsonNode partNode;
            try {
                partNode = m.readTree(part);
            } catch (Exception e) {
                System.out.println("Impossible de lire l'arbre du JWT");
                return null;
            }

            JsonNode fieldNode = partNode.get(field);

            if (fieldNode != null) {
                return fieldNode.asText();
            }
        }

        return null;
    }
}
