package com.br.jfouro.authservice.api.controller;

import com.br.jfouro.authservice.api.dto.UserDTO;
import com.br.jfouro.authservice.domain.User;
import com.br.jfouro.authservice.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole()));
    }

}

