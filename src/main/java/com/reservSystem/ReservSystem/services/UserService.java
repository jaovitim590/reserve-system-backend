package com.reservSystem.ReservSystem.services;

import com.reservSystem.ReservSystem.DTOS.UpdatePerfilDto;
import com.reservSystem.ReservSystem.DTOS.UserDto;
import com.reservSystem.ReservSystem.exceptions.EmailJaCadastradoException;
import com.reservSystem.ReservSystem.exceptions.RecursoNaoEncontradoException;
import com.reservSystem.ReservSystem.exceptions.RoleInvalidaException;
import com.reservSystem.ReservSystem.models.Role;
import com.reservSystem.ReservSystem.models.User;
import com.reservSystem.ReservSystem.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public void createUser(UserDto user) {
        if (repository.existsByEmail(user.email())) {
            throw new EmailJaCadastradoException();
        }

        User u = new User();

        try {
            u.setRole(Role.valueOf(user.role()));
        } catch (IllegalArgumentException e) {
            throw new RoleInvalidaException();
        }

        u.setEmail(user.email());
        u.setPassword(encoder.encode(user.password()));
        u.setName(user.name());
        u.setData_criado(Instant.now());

        repository.save(u);
    }

    public void deleteUser(Integer id) {
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Usuário");
        }
        repository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUser(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário"));
    }

    public User findByEmail(String email) {
        User user = repository.findByEmail(email);
        if (user == null) {
            throw new RecursoNaoEncontradoException("Usuário");
        }
        return user;
    }

    public void update(UserDto user) {
        User existingUser = repository.findByEmail(user.email());
        if (existingUser == null) {
            throw new RecursoNaoEncontradoException("Usuário");
        }

        Optional.ofNullable(user.name())
                .filter(name -> !name.isBlank())
                .ifPresent(existingUser::setName);

        Optional.ofNullable(user.password())
                .filter(pwd -> !pwd.isBlank())
                .map(encoder::encode)
                .ifPresent(existingUser::setPassword);

        Optional.ofNullable(user.role())
                .filter(roleStr -> !roleStr.isBlank())
                .ifPresent(roleStr -> {
                    try {
                        existingUser.setRole(Role.valueOf(roleStr));
                    } catch (IllegalArgumentException e) {
                        throw new RoleInvalidaException();
                    }
                });

        repository.save(existingUser);
    }

    public User updatePerfil(Integer userId, UpdatePerfilDto updateDto) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário"));

        Optional.ofNullable(updateDto.name())
                .filter(name -> !name.isBlank())
                .ifPresent(user::setName);

        return repository.save(user);
    }

    public boolean existByEmail(String email) {
        return repository.existsByEmail(email);
    }

    public boolean isadmin(String email) {
        User user = repository.findByEmail(email);
        return user != null && user.getRole() == Role.ADMIN;
    }
}