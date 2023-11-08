package com.example.galacticMessengerClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.galacticMessengerClient.Console.ConsoleUser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.*;

@SpringBootApplication
public class GalacticMessengerClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(GalacticMessengerClientApplication.class, args);

        if (checkArgs(args)) {
            try {
                ConsoleUser consoleUser = new ConsoleUser(args);
                consoleUser.ConsoleUseGalacticMessenger();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    private static boolean checkArgs(String[] args) {
        if (args.length == 2) {
            // regex basé sur le format de port
            String portPattern = "^[0-9]{4}$";
            // vérification de la validité de l'adresse ip par rapport au numéro de port
            Pattern pattern = Pattern.compile(portPattern);
            Matcher matcherPort = pattern.matcher(args[1]);
            // vérifie l'ip et le port
            if (checkIpFormat(args[0]) && matcherPort.matches()) {
                System.out.println("\nAdresse et numero de port valides\nBienvenue !\n");
                return true;
            }
            else {
                System.out.println("\nAdresse et numero de port invalides !\n\n");
                System.exit(0);
            }
        } else {
            System.out.printf("\nNombre d'arguments invalides : 2 attendus, %d recus\n\n", args.length);
            System.exit(0);
        }
        return false;
    }

    private static boolean checkIpFormat(String ip) {
        // si l'ip contient bien des points
        if (ip.contains(".")) {
            // sépare les octets
            String[] ipArray = ip.split("\\.");
            // s'il y a bien 4 octets
            if (ipArray.length == 4) {
                // pour chaque octet
                for (String octet : ipArray) {
                    // pour chaque char de l'octet
                    for (char o : octet.toCharArray()) {
                        // si le char n'est pas un nombre
                        if(!Character.isDigit(o)) {
                            return false;
                        }
                        return true;
                    }
                }
            }
        }

        return false;
    }

    // private static boolean checkPortFormat(String port) {

    // }
}
