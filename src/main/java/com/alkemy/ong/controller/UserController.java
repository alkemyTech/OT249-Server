package com.alkemy.ong.controller;

import java.sql.Timestamp;
import java.util.UUID;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.model.Role;
import com.alkemy.ong.model.User;
import com.alkemy.ong.service.IRoleService;
import com.alkemy.ong.service.UserService;

@RestController
@AllArgsConstructor
@RequestMapping(value = "users")
public class UserController {

	private final UserService userService;

	private final IRoleService roleService;

	private final PasswordEncoder passwordEncode;

	@PostMapping("/auth/register")
	public ResponseEntity<User> registrarUsuario(@Valid @RequestBody UserDto userDto) {
		UUID uuid = UUID.randomUUID();
		Role rol = roleService.getRoleById(userDto.getRole().getId());
		boolean deleted = false;
		User user =  new User(uuid, userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(), passwordEncode.encode(userDto.getPassword()), userDto.getPhoto(), rol, new Timestamp(System.currentTimeMillis()), deleted);
		return new ResponseEntity<>(userService.guardarUsuario(user), HttpStatus.OK);
	}
    @GetMapping(value = "")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getPagedUsers(@RequestParam(defaultValue = "0", name = "page") int page,
                                      @RequestParam(defaultValue = "asc", name = "order") String order) {
        return ResponseEntity.ok( userService.getAllUsers(page, order) );
    }
}
