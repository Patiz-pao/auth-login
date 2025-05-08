package com.authentication.login.repository;

import com.authentication.login.entity.SessionToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionTokenRepo extends JpaRepository<SessionToken, String> {

    SessionToken findByUserId(String userId);
}
