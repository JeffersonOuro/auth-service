package com.br.jfouro.authservice.service;

import com.br.jfouro.authservice.api.dto.RegisterRequestDTO;
import com.br.jfouro.authservice.domain.User;
import com.br.jfouro.authservice.domain.UserRole;
import com.br.jfouro.authservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
public class AuthService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Function<RegisterRequestDTO, User> dtoToUser;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

        this.dtoToUser = dto -> new User(
                dto.login(),
                this.passwordEncoder.encode(dto.password()),
                dto.email(),
                Optional.<UserRole>ofNullable(dto.role()).orElse(UserRole.USER)
        );
    }

    public User register(RegisterRequestDTO data) {
        return Optional.ofNullable(data)
                .map(dtoToUser)
                .map(userRepository::save)
                .orElseThrow(() -> new IllegalArgumentException("Dados inválidos para registro"));
    }

}