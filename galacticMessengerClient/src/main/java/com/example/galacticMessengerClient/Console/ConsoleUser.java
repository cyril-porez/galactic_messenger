package com.example.galacticMessengerClient.Console;

import java.util.ArrayList;
import java.util.List;
// import org.yaml.snakeyaml.scanner.Scanner;
import java.util.Scanner;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.galacticMessengerClient.Request.RequestApi;

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
                    handleRegister(commandSplit, choiceCommand);
                    // request(commandSplit[1], commandSplit[2], adressServer, commandSplit[0]);
                    break;
                case "/help":
                    help();
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
            requestApi.request(commands[1], hashPassword(commands[2]), adressServer, choiceCommand);
        }
        else {
            System.out.println("La commande est incorrecte. Entrez '/help' pour voir les différentes commandes.\n");
        }
    }

    public String hashPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

}
