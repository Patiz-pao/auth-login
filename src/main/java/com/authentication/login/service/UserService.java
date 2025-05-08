package com.authentication.login.service;

import com.authentication.login.entity.RoleEntity;
import com.authentication.login.entity.UserEntity;
import com.authentication.login.exceptions.AuthExceptions;
import com.authentication.login.repository.RoleRepo;
import com.authentication.login.repository.UserRepo;
import com.authentication.login.service.domain.LoginResponse;
import com.authentication.login.util.GenericResponse;
import com.authentication.login.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    private final RoleRepo roleRepo;
    private final UserRepo userRepo;

    public GenericResponse<LoginResponse> login(String username, String password){

        UserEntity user = userRepo.findByUsername(username);

        if (user != null){
            if (passwordEncoder.matches(password, user.getPassword())){

                String token = jwtUtil.generateToken(username, user.getRole());
                LoginResponse loginResponse = new LoginResponse(user.getId(), user.getUsername(), user.getPassword(), user.getRole(), token);

                return new GenericResponse<>(HttpStatus.OK, "Login success", loginResponse);

            }else {
                throw new AuthExceptions.IncorrectPasswordException("Incorrect password");
            }
        }else {
            throw new AuthExceptions.NotFoundException("User Not Found");
        }
    }

    public GenericResponse<UserEntity> getUser(String username){

        UserEntity user = userRepo.findByUsername(username);

        if (user != null){
            return new GenericResponse<>(HttpStatus.OK, "User found", user);
        }else {
            throw new AuthExceptions.NotFoundException("User Not Found");
        }
    }

    public GenericResponse<List<UserEntity>> getAllUsers(){

        List<UserEntity> user = userRepo.findAll();
        if (!user.isEmpty()) {
            return new GenericResponse<>(HttpStatus.OK, "Users found", user);
        } else {
            throw new AuthExceptions.NotFoundException("User Not Found");
        }
    }

    public GenericResponse<UserEntity> createUser(UserEntity user){

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        RoleEntity role = roleRepo.findByRoleId(user.getRole());
        if (role == null){
            throw new AuthExceptions.NotFoundException("Role Not Found");
        }

        userRepo.save(user);

        return new GenericResponse<>(HttpStatus.CREATED, "Create success", user);
    }
}
