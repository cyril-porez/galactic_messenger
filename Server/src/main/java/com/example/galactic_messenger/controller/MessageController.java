package com.example.galactic_messenger.controller;

import com.example.galactic_messenger.Interfaces.UserRepository;
import com.example.galactic_messenger.Services.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RequestMapping("/api/user")
@RestController
public class MessageController {
    private Test service;
    private UserRepository repo;

    public MessageController(Test testService, UserRepository repository) {
        this.service = testService;
        this.repo = repository;
    }

    @PostMapping("/private_chat")
    public CompletableFuture<ResponseEntity<ApiResponse>> private_chat(@RequestParam String asked_user, @RequestParam String user1) {
        /* cherche le nom d'user dans le server */

        return service.private_chat(asked_user, user1)
                .handle((result, ex) -> {
                    ApiResponse response = new ApiResponse();
                    if (result.equals("L'utilisateur existe")) {
                        try {
                            response.setStatus(HttpStatus.OK.value());
                            response.setMessage("Utilisateur trouvé !");
                            /*response.sendMessageToAskedUser()*/
                            /*
                            Users u = repo.findByName(name);
                            // JSONObject data = new JSONObject();
                            Map<String, Object> data = new HashMap<>();

                            data.put("id", u.getId());
                            data.put("name", u.getName());*/

                            response.setData(null);
                            return ResponseEntity.status(HttpStatus.OK).body(response);
                        } catch (Exception e) {
                            ApiResponse errorResponse = new ApiResponse();
                            errorResponse.setStatus(HttpStatus.BAD_GATEWAY.value());
                            errorResponse.setMessage("Une erreur s'est produite");
                            // Vous pouvez également inclure des détails supplémentaires sur l'exception, si nécessaire
                            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(errorResponse);
                        }
                    } else if (result.equals("L'utilisateur n'existe pas !")) {
                        try {
                            response.setStatus(HttpStatus.BAD_REQUEST.value());
                            response.setMessage("Utilisateur non trouvé !");
                            response.setData(null);
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

                        } catch (Exception e) {
                            ApiResponse errorResponse = new ApiResponse();
                            errorResponse.setStatus(HttpStatus.FORBIDDEN.value());
                            errorResponse.setMessage("Une erreur s'est produite");
                            // Vous pouvez également inclure des détails supplémentaires sur l'exception, si nécessaire
                            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(errorResponse);
                        }
                    }
                    else {
                        response.setStatus(HttpStatus.FORBIDDEN.value());
                        response.setMessage("Ces identifiants n'existent pas ");
                        // response.setData("test");
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                    }
                });
    }
    /*
    @PostMapping("/accept")
    public CompletableFuture<ResponseEntity<ApiResponse>> accept(@RequestParam String name){

        return
    }*/
}
