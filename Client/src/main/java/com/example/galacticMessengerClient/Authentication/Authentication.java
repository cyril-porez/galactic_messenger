package com.example.galacticMessengerClient.Commands;


import com.example.galacticMessengerClient.Session;
import com.example.galacticMessengerClient.Controllers.ApiResponse;
import com.example.galacticMessengerClient.Request.RequestApi;
import com.example.galacticMessengerClient.Services.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Authentication {

    String addressIp;
    String port;
    private String adressServer;
    private RequestApi requestApi;
    private JwtService jwtService;

    public Authentication(String[] args){
        requestApi = new RequestApi();
        jwtService = new JwtService();
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
                String username = jwtService.getDataFromJWT((String) Session.getData("token"), "sub");
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

}
