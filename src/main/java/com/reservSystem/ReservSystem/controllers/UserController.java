package com.reservSystem.ReservSystem.controllers;

import com.reservSystem.ReservSystem.DTOS.LoginDto;
import com.reservSystem.ReservSystem.DTOS.UserDto;
import com.reservSystem.ReservSystem.models.User;
import com.reservSystem.ReservSystem.services.UserService;
import com.reservSystem.ReservSystem.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping
    public List<User> getUsers(){
        return service.getAllUsers();
    }

    @PostMapping
    public String addUser(UserDto user) throws Exception {
        return service.createUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDto dto) throws Exception {
        User user = service.findByEmail(dto.email());

        if (!encoder.matches(dto.password(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return JwtService.generateToken(user);
    }
}
