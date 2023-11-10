package com.example.galactic_messenger.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.JSONObject;

import com.example.galactic_messenger.Interfaces.UserRepository;
import com.example.galactic_messenger.Services.Test;
import com.example.galactic_messenger.model.Users;

@RequestMapping("/api/user")
@RestController
public class UserController {

    private Test service;
    private UserRepository repo;

    @Autowired
    public UserController(Test testService, UserRepository repository) {
        this.service = testService;
        this.repo = repository;
    }
    
    // public UserController() {
    // // Initialisation par défaut, par exemple, vous pouvez instancier un
    // UserRepository ici si nécessaire.
    // }

    @PostMapping("/register")
    public CompletableFuture<ResponseEntity<ApiResponse>> register(@RequestParam String name,
            @RequestParam String password) {
        return service.registerUser(name, password)
                .handle((result, ex) -> {
                    ApiResponse response = new ApiResponse();

                    if (result.equals("Inscription réussie")) {
                        response.setStatus("success");
                        response.setMessage("Vous êtes inscrits!");

                        JSONObject data = new JSONObject();
                        data.put("name", name);
                        data.put("password", password);
                        response.setData(data);

                        return ResponseEntity.status(HttpStatus.OK).body(response);
                    } else if (result.equals("Ce nom existe déjà")) {
                        response.setStatus("error");
                        response.setMessage("Les identifiants sont déjà utilisés");
                        response.setData(null);
                        System.out.println(response);
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                    } else {
                        response.setStatus("test");
                        response.setMessage("test");
                        // response.setData("test");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                    }
                });
    }

    @PostMapping("/login")
    public CompletableFuture<ResponseEntity<ApiResponse>> login(@RequestParam String name,
            @RequestParam String password) {
        return service.userlogin(name, password)
                .handle((result, ex) -> {
                    ApiResponse response = new ApiResponse();
                    if (result.equals("Vous êtes connectés !")) {
                        response.setStatus("sucess");
                        response.setMessage("Vous vous êtes connecté avec succès !");

                        Users u = repo.findByName(name);
                        JSONObject data = new JSONObject();
                        data.put("id", u.getId());
                        data.put("name", u.getName());

                        response.setData(data);
                        return ResponseEntity.status(HttpStatus.OK).body(response);
                    } else if (result.equals("Nom d'utilisateur ou mot de passe incorrect")) {
                        response.setStatus("error");
                        response.setMessage("Les identifiants sont incorects");
                        response.setData(null);
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                    } else {
                        response.setStatus("test");
                        response.setMessage("Ces identifiants n'existent pas ");
                        // response.setData("test");
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                    }

                });
    }
}
