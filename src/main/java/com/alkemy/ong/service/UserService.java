package com.alkemy.ong.service;

import com.alkemy.ong.dto.LoginRequestDTO;
import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.dto.UserResponseDto;
import com.alkemy.ong.model.User;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public interface UserService extends UserDetailsService{

	User guardarUsuario(User user) throws IOException;
	Page<UserDto> getAllUsers(int page, String order);
	public Optional<User> findById(String id);
	public boolean deleteUser(String id);

	UserDto authenticatedUser() throws Exception;
	public UserResponseDto login(LoginRequestDTO loginRequestDTO);
}
