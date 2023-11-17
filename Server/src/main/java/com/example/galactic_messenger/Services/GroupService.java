package com.example.galactic_messenger.Services;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.galactic_messenger.Interfaces.GroupRepository;

@Service
public class GroupService {
    
    private GroupRepository repo;

    @Autowired
    public GroupService(GroupRepository repository) {
        this.repo = repository;
    }

    // @Async
    // public CompletableFuture<String> createGroup(String name, Optional<String> password) {
    //     return CompletableFuture.supplyAsync(() -> {
    //         String 
    //     });
    // }
}
