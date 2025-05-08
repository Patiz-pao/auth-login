package com.authentication.login.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

@Entity
@Table(name = "session_token")
public class SessionToken {
    @Id
    private String userId;

    private String token;

    private LocalDateTime issuedAt;

}
