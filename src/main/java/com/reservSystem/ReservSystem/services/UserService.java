package com.reservSystem.ReservSystem.services;

import com.reservSystem.ReservSystem.DTOS.UserDto;
import com.reservSystem.ReservSystem.models.Role;
import com.reservSystem.ReservSystem.models.User;
import com.reservSystem.ReservSystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public void createUser(UserDto user) throws Exception {
        if(repository.existsByEmail(user.email())){
            throw new Exception("User with email already exists");
        }

        User u = new User();

        try {
            u.setRole(Role.valueOf(user.role()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Role inválida");
        }
        String password = encoder.encode(user.password());
        u.setEmail(user.email());
        u.setPassword(password);
        u.setName(user.name());
        u.setData_criado(Instant.now());

        repository.save(u);
    }

    public void deleteUser(Integer id) throws Exception {
        if(repository.existsById(id)){
            repository.deleteById(id);
        }else {
            throw new Exception("User not found");
        }
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUser(Integer id) throws Exception {
        if(repository.existsById(id)){
            return repository.findById(id).get();
        }else {
            throw new Exception("User not found");
        }
    }
}
