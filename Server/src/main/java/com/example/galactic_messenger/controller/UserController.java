package com.example.galactic_messenger.controller;

import java.util.concurrent.CompletableFuture;

import com.example.galactic_messenger.security.JwtAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.HashMap;

import com.example.galactic_messenger.Interfaces.UserRepository;
import com.example.galactic_messenger.Services.JwtService;
import com.example.galactic_messenger.Services.Test;
import com.example.galactic_messenger.model.Users;
import com.example.galactic_messenger.security.MyUserDetails;

@RequestMapping("/api/user")
@RestController
public class UserController {

    private final Test service;
    private final UserRepository repo;

    private final JwtService jwtService;


    @Autowired
    public UserController(Test testService, UserRepository repository, JwtService jwtService) {
        this.service = testService;
        this.repo = repository;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public CompletableFuture<ResponseEntity<ApiResponse>> register(@RequestParam String name,
            @RequestParam String password) {
        return service.registerUser(name, password)
                .handle((result, ex) -> {
                    ApiResponse response = new ApiResponse();

                    if (result.equals("Inscription réussie")) {
                        try {
                            response.setStatus(HttpStatus.OK.value());
                            response.setMessage("Vous êtes inscrits!");
                            Map<String, Object> dataMap = new HashMap<>();
                            dataMap.put("name", name);
                            dataMap.put("password", password);
                            response.setData(dataMap);

                            return ResponseEntity.status(HttpStatus.OK).body(response);
                            
                        } catch (Exception e) {
                            ApiResponse errorResponse = new ApiResponse();
                            errorResponse.setStatus(HttpStatus.BAD_GATEWAY.value());
                            errorResponse.setMessage("Une erreur s'est produite");
                            // Vous pouvez également inclure des détails supplémentaires sur l'exception, si nécessaire
                            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(errorResponse);
                        }
                        
                    } else if (result.equals("Ce nom existe déjà")) {
                        try {
                            response.setStatus(HttpStatus.BAD_REQUEST.value());
                            response.setMessage("Les identifiants sont déjà utilisés");
                            response.setData(null);
                            System.out.println(response);
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                        } catch (Exception e) {
                            ApiResponse errorResponse = new ApiResponse();
                            errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
                            errorResponse.setMessage("Une erreur s'est produite");
                            // Vous pouvez également inclure des détails supplémentaires sur l'exception, si nécessaire
                            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(errorResponse);
                        }
                       
                    } else {
                        response.setStatus(HttpStatus.NOT_FOUND.value());
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
                        try {
                            response.setStatus(HttpStatus.OK.value());
                            response.setMessage("Vous vous êtes connecté avec succès !");
    
                            Users user = repo.findByName(name);
                            user.setIsConnected(true);
                            repo.save(user);
                            // JSONObject data = new JSONObject();
                            Map<String, Object> data = new HashMap<>();

                            MyUserDetails userDetails = new MyUserDetails(user.getId(), user.getName());
                            String token = jwtService.generateToken(userDetails);
                            System.out.println(token); // utilise pour faire des tests

                            JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(token);
                            authenticationToken.setDetails(userDetails);

                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                            data.put("id", user.getId());
                            data.put("name", user.getName());
                            data.put("token", token);
                            response.setData(data);
                            return ResponseEntity.status(HttpStatus.OK).body(response);
                            
                        } catch (Exception e) {
                            ApiResponse errorResponse = new ApiResponse();
                            errorResponse.setStatus(HttpStatus.BAD_GATEWAY.value());
                            errorResponse.setMessage("Une erreur s'est produite");
                            // Vous pouvez également inclure des détails supplémentaires sur l'exception, si nécessaire
                            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(errorResponse);
                        }
                    } else if (result.equals("Nom d'utilisateur ou mot de passe incorrect")) {
                        try {
                            response.setStatus(HttpStatus.BAD_REQUEST.value());
                            response.setMessage("Les identifiants sont incorects");
                            response.setData(null);
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                            
                        } catch (Exception e) {
                            ApiResponse errorResponse = new ApiResponse();
                            errorResponse.setStatus(HttpStatus.BAD_GATEWAY.value());
                            errorResponse.setMessage("Une erreur s'est produite");
                            // Vous pouvez également inclure des détails supplémentaires sur l'exception, si nécessaire
                            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(errorResponse);
                        }
                    } else {
                        response.setStatus(HttpStatus.BAD_REQUEST.value());
                        response.setMessage("Ces identifiants n'existent pas ");
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                    }
                });
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(@RequestHeader("Authorization") String authorization) {

        String name, token = "";
        Authentication context = SecurityContextHolder.getContext().getAuthentication();

        if (authorization != null && authorization.startsWith("Bearer ") && context != null){
            token = authorization.substring(7);
            try {
                name = JwtService.verifyToken(token)[1];   // user name
                repo.findByName(name).setIsConnected(false);
            } catch (Exception e) {
                ApiResponse errorResponse = new ApiResponse();
                errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
                errorResponse.setMessage("Token is invalid.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            ApiResponse response = new ApiResponse();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Vous êtes déconnecté");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        else {
            ApiResponse response = new ApiResponse();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setMessage("Vous n'etes pas autorisé à faire cette requête.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
