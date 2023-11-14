package com.example.galactic_messenger.controller;

import com.example.galactic_messenger.Interfaces.UserRepository;
import com.example.galactic_messenger.Services.JwtService;
import com.example.galactic_messenger.Services.LoggedUserService;
import com.example.galactic_messenger.Services.Test;
import com.example.galactic_messenger.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequestMapping("/api/user")
@RestController
public class LoggedController {
    private LoggedUserService userService;
    private UserRepository repo;

    @Autowired
    private JwtService jwtService;

    public LoggedController(LoggedUserService userService, UserRepository repository) {
        this.userService = userService;
        this.repo = repository;
    }
    /*
    @GetMapping("/online_users")
    public List online_users(){ /* Recuperer un objet d'objet contenant son name, son id
        /*return userService.online_users()
                .handle((result, ex)) -> {
            ApiResponse response = new ApiResponse();
            if (result.equals == "utilisateurs connectés"){
                try {
                    response.setStatus(HttpStatus.OK.value());
                    response.setMessage("Liste des utilisateurs connectés ");
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } catch (Exception e) {
                    ApiResponse errorResponse = new ApiResponse();
                    errorResponse.setStatus(HttpStatus.BAD_GATEWAY.value());
                    errorResponse.setMessage("Une erreur s'est produite");
                    // Vous pouvez également inclure des détails supplémentaires sur l'exception, si nécessaire
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(errorResponse);
            }
        }*/

    }

