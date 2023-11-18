package com.example.galacticMessengerClient.Commands;

public class Help {


    public void helpUserNotConnected() {
        System.out.println("\nAfin de vous aider à utiliser l'application voici la liste de toutes les commandes:\n");
        System.out.println("Inscription:");
        System.out.println("- /register \"nom_d'utilisateur\" \"mot_de_passe\"\n");
        System.out.println("Connexion:");
        System.out.println("- /login \"nom_d'utilisateur\" \"mot_de_passe\"\n");
        System.out.println("Pour fermer le client: ");
        System.out.println("- /exit\n");

    }

    public void helpUserConnected() {
        /* Commands liste utilisateurs connectés */
        System.out.println("\nVoici la liste des différentes commandes afin de discuter avec les autres utilisateurs\n");
        System.out.println("Voir la liste des utilisateurs connectés");
        System.out.println("- /online_users \n");

        /* Commands messages one_to_one */
        System.out.println("Demande de chat");
        System.out.println("- /private_chat \"utilisateur 2\"\n");
        System.out.println("Accepter ou refuser la connexion");
        System.out.println("- /accept \"utilisateur 1\"");
        System.out.println("- /decline \"utilisateur 1\"\n");
        System.out.println("Quitter le chat");
        System.out.println("- /exit_private_chat \n");

        /* Commandes messages group_chat */
        System.out.println("Créer un groupe chat");
        System.out.println("- /create_group \"nom du groupe\"\n");
        System.out.println("Rejoindre un groupe chat");
        System.out.println("- /join_group \"nom du groupe\"\n");
        System.out.println("Envoyer un message dans le groupe ");
        System.out.println("- /msg_group \"nom du groupe\" \"message\"\n");
        System.out.println("Quitter un groupe chat");
        System.out.println("- /exit_group \"nom du groupe\"\n");

        /* Commandes upload/download en UDP */
        System.out.println("Upload un fichier");
        System.out.println("- /upload_file \"chemin_du_fichier\"\n");
        System.out.println("Download un fichier");
        System.out.println("- /download_file \"chemin_du_fichier\"\n");
        System.out.println("Lister les fichiers d'une conversation ou groupe");
        System.out.println("- /list_files \n");

        /* Commandes pour chat securisé dans un groupe */
        System.out.println("Créer un groupe sécurisé");
        System.out.println("- /create_secured_group \"nom_du_group\" \"mot_de_passe\"\n");
        System.out.println("Rejoindre un groupe sécurisé");
        System.out.println("- /join_secured_group \"nom_du_group\" \"mot_de_passe\"\n");

        /* Deconnexion et quitter l'application */
        System.out.println("Pour se déconnecter"); /* Fonction non demandé dans le sujet */
        System.out.println("- /logout \n");
        System.out.println("Pour fermer le client: ");
        System.out.println("- /exit\n");

    }
}
