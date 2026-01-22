package com.br.jfouro.authservice.api.dto;

import com.br.jfouro.authservice.domain.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
        @NotBlank @Size(min = 3, max = 50) String login,
        @NotBlank @Size(min = 6) String password,
        @Email String email,
        UserRole role
) {}
