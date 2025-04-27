package com.authentication.login.service.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private UUID id;
    private String username;
    private String password;
    private String role;
    private String token;
}
