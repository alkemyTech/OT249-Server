package com.alkemy.ong.controller;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.alkemy.ong.Utils.JwtUtil;
import com.alkemy.ong.dto.LoginRequestDTO;
import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.dto.UserResponseDto;
import com.alkemy.ong.model.Role;
import com.alkemy.ong.model.User;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.IRoleService;
import com.alkemy.ong.service.UserService;

import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private PasswordEncoder passwordEncode;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtil jwtUtil;

	@PostMapping("/auth/register")
	public ResponseEntity<String> registrarUsuario(@Valid @RequestBody UserDto userDto) throws IOException {
		Role rol = roleService.getRoleById(userDto.getRole().getId());
		boolean deleted = false;
		User user =  new User(userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(), passwordEncode.encode(userDto.getPassword()), userDto.getPhoto(), rol, new Timestamp(System.currentTimeMillis()), deleted);
		return new ResponseEntity<>("usuario guardado", HttpStatus.OK);
	}
	@GetMapping(value = "/users")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> getPagedUsers(@RequestParam(defaultValue = "0", name = "page") int page,
										   @RequestParam(defaultValue = "asc", name = "order") String order) {
		return ResponseEntity.ok( userService.getAllUsers(page, order) );
	}

	@PatchMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") String id, @RequestBody Map<Object, Object> fields) throws IOException {
		try {
			User user = userService.findById(id).get(); 
			fields.forEach((key, value) -> {
				Field field = ReflectionUtils.findField(user.getClass(), (String) key);
				field.setAccessible(true);
				ReflectionUtils.setField(field, user, value);
			});
			return new ResponseEntity<>(userService.guardarUsuario(user), HttpStatus.OK);
		} catch (NullPointerException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<Boolean> deleteUser(@PathVariable("id") String id) {
		boolean userEliminado = userService.deleteUser(id);
		if (userEliminado) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/auth/me")
	public ResponseEntity<UserDto>AuthenticatedUser () throws Exception {

		return new ResponseEntity<>(userService.authenticatedUser(),HttpStatus.OK);
	}
}
