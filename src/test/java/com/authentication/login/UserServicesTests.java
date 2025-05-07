package com.authentication.login;

import com.authentication.login.exceptions.AuthExceptions;
import com.authentication.login.entity.RoleEntity;
import com.authentication.login.entity.UserEntity;
import com.authentication.login.repository.RoleRepo;
import com.authentication.login.repository.UserRepo;
import com.authentication.login.service.UserService;
import com.authentication.login.service.domain.LoginResponse;
import com.authentication.login.util.GenericResponse;
import com.authentication.login.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserServicesTests {

	@Mock
	private JwtUtil jwtUtil;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private UserRepo userRepo;

	@Mock
	private RoleRepo roleRepo;

	@InjectMocks
	private UserService userService;

	@Test
	void testLogin_Success() {

		String username = "testUser";
		String password = "testPassword";
		UserEntity user = new UserEntity();
		user.setId(UUID.randomUUID());
		user.setUsername(username);
		user.setPassword(password);
		user.setRole("4d57aa8e-61d4-401c-8578-e6422281788b");

		Mockito.when(userRepo.findByUsername(username)).thenReturn(user);
		Mockito.when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);
		Mockito.when(jwtUtil.generateToken(username, "4d57aa8e-61d4-401c-8578-e6422281788b")).thenReturn("mockToken");


		GenericResponse<LoginResponse> response = userService.login(username, password);

		assertEquals(HttpStatus.OK, response.getStatus());
		assertNotNull(response.getData());
		assertEquals("mockToken", response.getData().getToken());
	}

	@Test
	void testLogin_UserIsNull() {
		String username = "testUser";
		String password = "testPassword";
		UserEntity user = new UserEntity();
		user.setId(UUID.randomUUID());
		user.setUsername(username);
		user.setPassword(password);
		user.setRole("4d57aa8e-61d4-401c-8578-e6422281788b");

		Mockito.when(userRepo.findByUsername(username)).thenReturn(null);
		Mockito.when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);
		Mockito.when(jwtUtil.generateToken(username, "4d57aa8e-61d4-401c-8578-e6422281788b")).thenReturn("mockToken");

		try {
			GenericResponse<LoginResponse> response = userService.login(username, password);
			assertEquals(user, response.getData());
		} catch (AuthExceptions.NotFoundException e) {
			assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		}
	}

	@Test
	void testLogin_PasswordNotMatch() {
		String username = "testUser";
		String password = "testPassword";
		UserEntity user = new UserEntity();
		user.setId(UUID.randomUUID());
		user.setUsername(username);
		user.setPassword(password);
		user.setRole("4d57aa8e-61d4-401c-8578-e6422281788b");

		Mockito.when(userRepo.findByUsername(username)).thenReturn(user);
		Mockito.when(passwordEncoder.matches(password, user.getPassword())).thenReturn(false);
		Mockito.when(jwtUtil.generateToken(username, "4d57aa8e-61d4-401c-8578-e6422281788b")).thenReturn("mockToken");

		try {
			GenericResponse<LoginResponse> response = userService.login(username, password);
			assertEquals(user, response.getData());
		} catch (AuthExceptions.IncorrectPasswordException e) {
			assertEquals(HttpStatus.UNAUTHORIZED, e.getStatus());
		}
	}

	@Test
	void testGetUser_Success() {

		String username = "user";
		String password = "1234";
		UserEntity user = new UserEntity();
		user.setId(UUID.randomUUID());
		user.setUsername(username);
		user.setPassword(password);
		user.setRole("4d57aa8e-61d4-401c-8578-e6422281788b");

		Mockito.when(userRepo.findByUsername(username)).thenReturn(user);
		Mockito.when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);
		Mockito.when(jwtUtil.generateToken(username, "4d57aa8e-61d4-401c-8578-e6422281788b")).thenReturn("mockToken");


		GenericResponse<UserEntity> response = userService.getUser(username);

		assertEquals(HttpStatus.OK, response.getStatus());
		assertNotNull(response.getData());
	}

	@Test
	void testGetUser_UserIsNull() {
		String username = "testUser";
		String password = "testPassword";
		UserEntity user = new UserEntity();
		user.setId(UUID.randomUUID());
		user.setUsername(username);
		user.setPassword(password);
		user.setRole("4d57aa8e-61d4-401c-8578-e6422281788b");

		Mockito.when(userRepo.findByUsername(username)).thenReturn(null);
		Mockito.when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);
		Mockito.when(jwtUtil.generateToken(username, "4d57aa8e-61d4-401c-8578-e6422281788b")).thenReturn("mockToken");

		try {
			GenericResponse<UserEntity> response = userService.getUser(username);
			assertEquals(user, response.getData());
		} catch (AuthExceptions.NotFoundException e) {
			assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		}
	}

	@Test
	void testGetAllUser_Success() {

		UserEntity user = new UserEntity();
		String username = "user";
		String password = "1234";
		user.setId(UUID.randomUUID());
		user.setUsername("user");
		user.setPassword("1234");
		user.setRole("4f8ba3ad-934d-4af8-9fc9-e48a74b1253c");

		UserEntity user2 = new UserEntity();
		user2.setId(UUID.randomUUID());
		user2.setUsername("user2");
		user2.setPassword("1234");
		user2.setRole("4f8ba3ad-934d-4af8-9fc9-e48a74b1253c");

		List<UserEntity> userList = Arrays.asList(user, user2);

		Mockito.when(userRepo.findAll()).thenReturn(userList);
		Mockito.when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);
		Mockito.when(jwtUtil.generateToken(username, "4f8ba3ad-934d-4af8-9fc9-e48a74b1253c")).thenReturn("mockToken");


		GenericResponse<List<UserEntity>> response = userService.getAllUsers();

		assertEquals(HttpStatus.OK, response.getStatus());
		assertNotNull(response.getData());
	}

	@Test
	void testGetAllUser_NotFound() {
		String username = "testUser";
		String password = "testPassword";
		UserEntity user = new UserEntity();
		user.setId(UUID.randomUUID());
		user.setUsername(username);
		user.setPassword(password);
		user.setRole("4d57aa8e-61d4-401c-8578-e6422281788b");

		Mockito.when(userRepo.findByUsername(username)).thenReturn(null);
		Mockito.when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);
		Mockito.when(jwtUtil.generateToken(username, "4d57aa8e-61d4-401c-8578-e6422281788b")).thenReturn("mockToken");

		try {
			GenericResponse<List<UserEntity>> response = userService.getAllUsers();
		} catch (AuthExceptions.NotFoundException e) {
			assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		}
	}

	@Test
	void testCreateUser_Success() {
		String username = "testUser";
		String password = "testPassword";
		UserEntity user = new UserEntity();
		user.setId(UUID.randomUUID());
		user.setUsername(username);
		user.setPassword(password);
		user.setRole("4d57aa8e-61d4-401c-8578-e6422281788b");

		RoleEntity role = new RoleEntity();
		role.setRoleId("4d57aa8e-61d4-401c-8578-e6422281788b");
		role.setName("Admin");

		Mockito.when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword123");

		Mockito.when(roleRepo.findByRoleId(user.getRole())).thenReturn(role);

		GenericResponse<UserEntity> response = userService.createUser(user);

		assertEquals(HttpStatus.CREATED, response.getStatus());
		assertEquals("Create success", response.getMessage());
		assertEquals(user, response.getData());
	}

	@Test
	void testCreateUser_RoleIsNull() {
		String username = "testUser";
		String password = "testPassword";
		UserEntity user = new UserEntity();
		user.setId(UUID.randomUUID());
		user.setUsername(username);
		user.setPassword(password);
		user.setRole("4d57aa8e-61d4-401c-8578-e6422281788b");

		Mockito.when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword123");

		Mockito.when(roleRepo.findByRoleId(user.getRole())).thenReturn(null);

		try {
			GenericResponse<UserEntity> response = userService.createUser(user);
			assertEquals(user, response.getData());
		} catch (AuthExceptions.NotFoundException e) {
			assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		}
	}


}
