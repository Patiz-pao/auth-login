package com.authentication.login;

import com.authentication.login.controller.UserController;
import com.authentication.login.entity.UserEntity;
import com.authentication.login.repository.UserRepo;
import com.authentication.login.service.UserService;
import com.authentication.login.service.domain.LoginResponse;
import com.authentication.login.util.GenericResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class UserControllerTests {

    @Mock
    private UserService userService;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserController userController;

    @Test
    public void testLogin_Success() {
        String username = "testUser";
        String password = "testPassword";
        UUID userId = UUID.randomUUID();
        LoginResponse loginResponse = new LoginResponse(userId, username, password, "4d57aa8e-61d4-401c-8578-e6422281788b", "mockToken");

        Mockito.when(userService.login(username, password)).thenReturn(new GenericResponse<>(HttpStatus.OK, "Login success", loginResponse));

        GenericResponse<LoginResponse> response = userController.login(username, password);

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Login success", response.getMessage());
        assertEquals("mockToken", response.getData().getToken());
    }

    @Test
    public void testGetUser_Success() {
        String username = "testUser";

        UserEntity mockUser = new UserEntity();
        mockUser.setUsername("testUser");
        mockUser.setRole("4d57aa8e-61d4-401c-8578-e6422281788b");

        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn(username);

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        Mockito.when(userService.getUser(username)).thenReturn(new GenericResponse<>(HttpStatus.OK, "User found", mockUser));

        GenericResponse<UserEntity> response = userController.getUser();

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("testUser", response.getData().getUsername());
    }

    @Test
    public void testGetAllUsers_Success() {
        UserEntity user1 = new UserEntity();
        user1.setUsername("user1");

        UserEntity user2 = new UserEntity();
        user2.setUsername("user2");

        List<UserEntity> users = Arrays.asList(user1, user2);

        Mockito.when(userService.getAllUsers()).thenReturn(new GenericResponse<>(HttpStatus.OK, "Users found", users));

        GenericResponse<List<UserEntity>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    public void testCreateUser_Success() {
        UserEntity user = new UserEntity();
        user.setId(UUID.randomUUID());
        user.setUsername("username");
        user.setPassword("password");
        user.setRole("4d57aa8e-61d4-401c-8578-e6422281788b");

        Mockito.when(userService.createUser(Mockito.any(UserEntity.class))).thenReturn(new GenericResponse<>(HttpStatus.CREATED, "User created", user));

        GenericResponse<UserEntity> response = userController.createUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertEquals("User created", response.getMessage());
    }


}
