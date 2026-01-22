package com.br.jfouro.authservice.api.dto;

import com.br.jfouro.authservice.domain.UserRole;

public record RegisterRequestDTO(String login, String password, UserRole role) {
}