package com.example.galacticMessengerClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.galacticMessengerClient.Commands.UserCommands;
import com.example.galacticMessengerClient.Console.ConsoleUser;

import java.util.regex.*;
import java.net.*;

@SpringBootApplication
public class GalacticMessengerClientApplication {
@Value("${server.address}")

    public static void main(String[] args) {

        SpringApplication.run(GalacticMessengerClientApplication.class, args);
        try {
            UserCommands userCommands = new UserCommands();
            if (checkArgs(args) && isServerAddressValid(args[0]) && findPort(args[0], Integer.parseInt(args[1]))) {
                ConsoleUser consoleUser = new ConsoleUser(args);
                userCommands.displayLaunchInstructionNotConnected();
                consoleUser.ConsoleUseGalacticMessenger();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isServerAddressValid(String serverAddress) {
        try {
            InetAddress server = InetAddress.getByName(serverAddress);
            return server.isReachable(5000); // Vérifie si l'adresse est atteignable dans un délai de 5 secondes
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("L'addresse saisie est incorrecte.");
            return false;
        }
    }

    private static boolean findPort(String ip, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(ip, port), 10_000 /* 10s */);
            return socket.isConnected();
        } 
        catch (Exception e) {
            System.out.println("\nPort inexistant ou fermé.\n");
            System.exit(0);
        } 

        return false;
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
                return true;
            } else {
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
                        if (!Character.isDigit(o)) {
                            return false;
                        }
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
