package com.example.galacticMessengerClient.Services;

import java.util.Base64;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtService {

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
