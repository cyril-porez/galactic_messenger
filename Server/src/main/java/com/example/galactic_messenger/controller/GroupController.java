package com.example.galactic_messenger.controller;

import java.util.concurrent.CompletableFuture;

import javax.swing.GroupLayout.Group;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.galactic_messenger.Interfaces.GroupRepository;
import com.example.galactic_messenger.Services.GroupService;

@RequestMapping("/api/group")
@RestController
public class GroupController {
    
    private GroupService service;
    private GroupRepository repo;

    
    public GroupController(GroupService service, GroupRepository repository) {
        this.service = service;
        this.repo = repository;
    }

    // @PostMapping("/create_group")
    // public CompletableFuture<ResponseEntity<ApiResponse>> createGroup(@RequestParam String name) {
        
    // }
}
