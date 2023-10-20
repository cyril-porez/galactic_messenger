package com.example.galactic_messenger.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;

@Controller
public class HelpTest {
  public HelpTest() {
    
  }

  public String CommandeHelp() {
    ArrayList<String> help = new ArrayList<String>();
    help.add("Pour vous inscrire uliser la commande suivante: /register \"nom_d'utilisateur\" \"mot_de_passe\".");
    help.add("Pour vous connecter utiliser la commande suivante: /login \"nom_d'utilisateur\" \"mot_de_passe\".");
    return help.get(0);
  }
}
