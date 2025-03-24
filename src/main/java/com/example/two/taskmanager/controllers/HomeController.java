package com.example.two.taskmanager.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    //Provjera da aplikacija radi
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Spring Boot aplikacija radi! 🚀");
    }
}
