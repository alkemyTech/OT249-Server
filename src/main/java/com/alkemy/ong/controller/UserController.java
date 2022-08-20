package com.alkemy.ong.controller;

import com.alkemy.ong.dto.LoginRequestDTO;
import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.model.Role;
import com.alkemy.ong.model.User;
import com.alkemy.ong.service.IRoleService;
import com.alkemy.ong.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Map;

@RestController
@AllArgsConstructor
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private PasswordEncoder passwordEncode;

	@Autowired
	ModelMapper modelMapper;

	@Operation(summary = "Endpoint to Register User")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User created",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = UserDto.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "User not found",
					content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden - Acceso no autorizado",
					content = @Content) })
	@PostMapping("/auth/register")
	public ResponseEntity<?> registrarUsuario(@Valid @RequestBody UserDto userDto) throws IOException {
		Role rol = roleService.getRoleById(userDto.getRole().getId());
		boolean deleted = false;
		User user =  new User(userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(), passwordEncode.encode(userDto.getPassword()), userDto.getPhoto(), rol, new Timestamp(System.currentTimeMillis()), deleted);
		userService.guardarUsuario(user);
		
		LoginRequestDTO loginReqDto = new LoginRequestDTO(userDto.getEmail(), userDto.getPassword());
		return new ResponseEntity<>(userService.login(loginReqDto), HttpStatus.OK);
	}


	@Operation(summary = "Endpoint to get registered users")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Users found",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = UserDto.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Users not found",
					content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden - Acceso no autorizado",
					content = @Content) })
	@GetMapping(value = "/users")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getPagedUsers(@RequestParam(defaultValue = "0", name = "page") int page,
										   @RequestParam(defaultValue = "asc", name = "order") String order) {
		return ResponseEntity.ok( userService.getAllUsers(page, order) );
	}

	@Operation(summary = "Endpoint to Find specific user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User found",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = UserDto.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "User not found",
					content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden - Acceso no autorizado",
					content = @Content) })
	@Parameters(value = {
			@Parameter(name = "id", description = "User id to update", required = true)})
	@PatchMapping("/users/{id}")
	@PreAuthorize("@userServiceImpl.validarId(#id)")
	public ResponseEntity<UserDto> updateUser(@PathVariable("id") String id, @RequestBody Map<Object, Object> fields) throws IOException {
		try {
			User user = userService.findById(id).get();
			fields.forEach((key, value) -> {
				Field field = ReflectionUtils.findField(user.getClass(), (String) key);
				field.setAccessible(true);
				if(key=="password") {
					ReflectionUtils.setField(field, user, passwordEncode.encode((CharSequence) value));
				}else {
					ReflectionUtils.setField(field, user, value);
				}
			});
			UserDto userDto = modelMapper.map(userService.guardarUsuario(user), UserDto.class);
			//return new ResponseEntity<>(userService.guardarUsuario(user), HttpStatus.OK);
			return new ResponseEntity<>(userDto, HttpStatus.OK);
		} catch (NullPointerException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@Operation(summary = "Endpoint to delete User")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User deleted",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = UserDto.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "User not found",
					content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden - Acceso no autorizado",
					content = @Content) })
	@Parameters(value = {
			@Parameter(name = "id", description = "User id to delete", required = true)})
	@DeleteMapping("/users/{id}")
	@PreAuthorize("@userServiceImpl.validarId(#id)")
	public ResponseEntity<Boolean> deleteUser(@PathVariable("id") String id) {
		boolean userEliminado = userService.deleteUser(id);
		if (userEliminado) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}


	@Operation(summary = "Endpoint to get authenticated User")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User found",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = UserDto.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "User not found",
					content = @Content),
			@ApiResponse(responseCode = "403", description = "Forbidden - Acceso no autorizado",
					content = @Content) })
	@GetMapping("/auth/me")
	@PreAuthorize("@userServiceImpl.validarId(#id)")
	public ResponseEntity<UserDto>AuthenticatedUser () throws Exception {

		return new ResponseEntity<>(userService.authenticatedUser(),HttpStatus.OK);
	}
}
