package com.reservSystem.ReservSystem.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class MainController {
    @GetMapping("/")
    public String home() {
        return "ReservSystem API is running!";
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
