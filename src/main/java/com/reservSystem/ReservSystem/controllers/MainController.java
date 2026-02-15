package com.reservSystem.ReservSystem.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {
    @GetMapping("/health")
    public ResponseEntity<String> SAUDE(){
        return ResponseEntity.ok().body("ta vivo");
    }
}
