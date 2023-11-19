package com.example.galacticMessengerClient.Services;

import java.util.Base64;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtService {

    public String getDataFromJWT(String jwt, String field) {
        // Divise le JWT
        String[] parts = jwt.split("\\.");
        if (parts.length == 3) {

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
                    break; // sors du "for" et retourne null
                }

                JsonNode fieldNode = partNode.get(field);

                if (fieldNode != null) {
                    return fieldNode.asText(); // retourne la valeur du champ recherché
                }
            }
        }

        return null; // si rien n'a été trouvé
    }
}
