package com.example.galactic_messenger.Services;

import com.example.galactic_messenger.Interfaces.UserRepository;
import com.example.galactic_messenger.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class LoggedUserService {
    private UserRepository repo;
    @Autowired
    public LoggedUserService(UserRepository repository){
        this.repo = repository;
    }
    public LoggedUserService(){};
/*
    @Async
    public CompletableFuture<List<Users>> online_users(){
        return CompletableFuture.supplyAsync(() -> {
            String str = "";
            StringBuilder built_string = new StringBuilder();
            repo.findAll();
            List<Users> get_all = repo.findAll();
            var po = repo.findAll();

            String str2 = "utilisateurs connectés";
            return po;
        });*/


        /*List<String> online_users = all_users.stream()
                    .filter(Users::getIsConnected)
                    .map(Users::getName)
                    .collect(Collectors.toList());*/

            /*for(String name : online_users) {
                built_string.append(name).append("\n");
            }*/
        /*str = built_string.toString();*/
    }

