package com.example.galacticMessengerClient.Console;

import java.util.Scanner;


import com.example.galacticMessengerClient.Session;
import com.example.galacticMessengerClient.Commands.Authentification;
import com.example.galacticMessengerClient.Commands.UserCommands;
import com.example.galacticMessengerClient.Services.JwtService;
public class ConsoleUser {

    private UserCommands userCommands;
    private Authentification handleUsers;
    private JwtService jwtService;

    public ConsoleUser(String[] args) {
        handleUsers = new Authentification(args);
        userCommands = new UserCommands();
        jwtService = new JwtService();
    }

    public void ConsoleUseGalacticMessenger() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez entrer votre commande!");

        boolean isRunning = true;
        String username = "";

        while (isRunning) {
            if (!Session.isEmpty() && Session.getData("token") != null) {
                try {
                    username = jwtService.getDataFromJWT((String)Session.getData("token"), "sub");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                username = "";
            }

            System.out.printf("[ %s ] > ", username == "" ? "Invité" : username);

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

    public void commandsNotConnected(String[] commandSplit, String choiceCommand) {
        // Commande disponibles pour l'invité
        switch (choiceCommand) {
            case "/register":
                handleUsers.handleRegister(commandSplit, choiceCommand);
                break;
            case "/login":
                handleUsers.handleLogin(commandSplit, choiceCommand);
                String username = jwtService.getDataFromJWT((String)Session.getData("token"), "sub");
                userCommands.displayLaunchInstructionConnected(username);
                break;
            case "/help":
                userCommands.helpUserNotConnected();
                break;
            case "/exit":
                System.out.println("Merci d'avoir utilisé GALACTIC MESSENGER");
                System.exit(0);
                break;
            default:
                System.out.println("Commande non reconnue par le système !");
                break;
        }
    }

    public void commandsConnected(String[] commandSplit, String choiceCommand) {
        // Commande disponibles pour l'invité
        switch (choiceCommand) {
            case "/help":
                userCommands.helpUserConnected();
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
            case "/logout":
                handleUsers.handleLogout(commandSplit, choiceCommand);
                userCommands.displayLaunchInstructionNotConnected();
                break;
            default:
                System.out.println("Commande non reconnue par le système !");
                break;
        }
    }
}
