package com.authentication.login.repository;

import com.authentication.login.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity, String> {

    UserEntity findByUsername(String username);
}
