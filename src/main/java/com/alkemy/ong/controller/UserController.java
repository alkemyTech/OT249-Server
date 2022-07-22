package com.alkemy.ong.controller;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.model.Role;
import com.alkemy.ong.model.User;
import com.alkemy.ong.service.IRoleService;
import com.alkemy.ong.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private PasswordEncoder passwordEncode;
	
	@PostMapping("/auth/register")
	public ResponseEntity<User> registrarUsuario(@Valid @RequestBody UserDto userDto) {
		UUID uuid = UUID.randomUUID();
		Role rol = roleService.getRoleById(userDto.getRole().getId());
		boolean deleted = false;
		User user =  new User(uuid, userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(), passwordEncode.encode(userDto.getPassword()), userDto.getPhoto(), rol, new Timestamp(System.currentTimeMillis()), deleted);
		return new ResponseEntity<>(userService.guardarUsuario(user), HttpStatus.OK);
	}
	
	@PatchMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") UUID id, @RequestBody Map<Object, Object> fields) {
		try {
			User user = userService.findById(id);
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
}
