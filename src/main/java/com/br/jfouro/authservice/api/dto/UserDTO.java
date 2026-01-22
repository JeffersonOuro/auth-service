package com.br.jfouro.authservice.api.dto;

import com.br.jfouro.authservice.domain.UserRole;

public record UserDTO(Long id, String username, String email, UserRole role) {}
