package com.example.galacticMessengerClient.Console;

import java.util.Scanner;

import com.example.galacticMessengerClient.Session;
import com.example.galacticMessengerClient.Commands.HandleUsers;
import com.example.galacticMessengerClient.Commands.Help;
public class ConsoleUser {

    private Help help;
    private HandleUsers handleUsers;

    public ConsoleUser(String[] args) {
        handleUsers = new HandleUsers(args);
        help = new Help();
    }

    public void displayLaunchInstructionNotConnected() {
        System.out.println("\n==================================================");
        System.out.println("               GALACTIC MESSENGER");
        System.out.println("==================================================");
        System.out.println("\nBienvenue sur Galactic Messenger.\n");
        System.out.println("Afin d'utiliser notre application, veuillez vous");
        System.out.println("inscrire ou vous connecter, si vous posséder déjà");
        System.out.println("un compte.\n");
        System.out.println("Inscription:");
        System.out.println("- /register \"nom_d'utilisateur\" \"mot_de_passe\"");
        System.out.println("Connexion:");
        System.out.println("- /login \"nom_d'utilisateur\" \"mot_de_passe\"");
        System.out.println("Demander de l'aide:");
        System.out.println("- /help\n");
    }

    public boolean displayLaunchInstructionConnected(String name) {
        System.out.println("\n==================================================");
        System.out.println("               GALACTIC MESSENGER");
        System.out.println("==================================================");
        System.out.printf("Bienvenue, %s dans le Galactic Messenger !\n", name);
        System.out.println("Pour vous aider à utiliser le chat, la commande");
        System.out.println("/help est diponible.\n");

        return false;
    }

    public void ConsoleUseGalacticMessenger() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez entrer votre commande!");

        boolean isRunning = true;
        String username = "";

        while (isRunning) {
            if (!Session.isEmpty() && Session.getData("token") != null) {
                try {
                    username = handleUsers.getDataFromJWT((String)Session.getData("token"), "sub");
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
                String username = handleUsers.getDataFromJWT((String)Session.getData("token"), "sub");
                displayLaunchInstructionConnected(username);
                break;
            case "/help":
                help.helpUserNotConnected();
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
                help.helpUserConnected();
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
                displayLaunchInstructionNotConnected();
                break;
            default:
                System.out.println("Commande non reconnue par le système !");
                break;
        }
    }
}
