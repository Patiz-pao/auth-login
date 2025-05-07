package com.authentication.login.controller;

import com.authentication.login.entity.UserEntity;
import com.authentication.login.service.UserService;
import com.authentication.login.service.domain.LoginResponse;
import com.authentication.login.util.GenericResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
public class UserController {

    private UserService userService;

    @PostMapping("/login")
    public GenericResponse<LoginResponse> login(@RequestParam String username, @RequestParam String password) {

        GenericResponse<LoginResponse> response = userService.login(username, password);
        log.info("Login successful for user: {}", username);

        return response;
    }

    @GetMapping("/user")
    public GenericResponse<UserEntity> getUser() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        GenericResponse<UserEntity> response = userService.getUser(username);
        log.info("Data user: {}", username);

        return response;
    }

    @GetMapping("/admin")
    public GenericResponse<List<UserEntity>> getAllUsers() {

        GenericResponse<List<UserEntity>> response = userService.getAllUsers();
        log.info("List of all users: {}", response.getData().size());

        return response;
    }

    @PostMapping("/new_user")
    @Operation(
            summary = "Create User or Admin",
            description = "Create user || admin",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "User Request",
                                            value = """
                                            {
                                              "username": "user",
                                              "password": "1234",
                                              "role": "4d57aa8e-61d4-401c-8578-e6422281788b"
                                            }"""
                                    ),
                                    @ExampleObject(
                                            name = "Admin Request",
                                            value = """
                                            {
                                              "username": "admin",
                                              "password": "1234",
                                              "role": "4f8ba3ad-934d-4af8-9fc9-e48a74b1253c"
                                            }"""
                                    )
                            }
                    )
            )
    )
    public GenericResponse<UserEntity> createUser(@RequestBody UserEntity user) {

        log.info("Creating user: {}", user.getUsername());

        return userService.createUser(user);
    }
}
