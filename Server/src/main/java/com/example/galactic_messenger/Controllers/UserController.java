package com.example.galactic_messenger.Controllers;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.HashMap;

import com.example.galactic_messenger.Interfaces.UserRepository;
import com.example.galactic_messenger.Security.MyUserDetails;
import com.example.galactic_messenger.Services.JwtService;
import com.example.galactic_messenger.Services.UserService;
import com.example.galactic_messenger.DTO.Users;

@RequestMapping("/api/user")
@RestController
public class UserController {

    private UserService service;
    private UserRepository repo;

    @Autowired
    private JwtService jwtService;

    public UserController(UserService testService, UserRepository repository) {
        this.service = testService;
        this.repo = repository;
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
    public ResponseEntity<ApiResponse> logout(@RequestParam String name) {
        repo.findByName(name).setIsConnected(false);

        ApiResponse response = new ApiResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Vous êtes déconnecté");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
