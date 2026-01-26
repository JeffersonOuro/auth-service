package com.br.jfouro.authservice.api.controller;

import com.br.jfouro.authservice.AuthResponse;
import com.br.jfouro.authservice.JwtService;
import com.br.jfouro.authservice.api.dto.LoginRequestDTO;
import com.br.jfouro.authservice.api.dto.LoginResponseDTO;
import com.br.jfouro.authservice.api.dto.RegisterRequestDTO;
import com.br.jfouro.authservice.domain.User;
import com.br.jfouro.authservice.service.AuthService;
import com.br.jfouro.authservice.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Function;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private  AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final JwtService jwtService;
    private  TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager,
                          AuthService authService, JwtService jwtService,
                          TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.jwtService = jwtService;
        this.tokenService = tokenService;
    }

    private final Function<LoginRequestDTO, String> gerarToken = dto -> {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.login(), dto.password())
        );
        return tokenService.generateToken((User) auth.getPrincipal());
    };

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO data) {
        var token = gerarToken.apply(data);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequestDTO data) {
        authService.register(data);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/validate")
    public AuthResponse validate(@RequestBody String token) {
        return jwtService.validateToken(token);
    }

}
