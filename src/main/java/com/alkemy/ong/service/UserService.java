package com.alkemy.ong.service;

import com.alkemy.ong.dto.LoginRequestDTO;
import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.dto.UserResponseDto;
import com.alkemy.ong.model.User;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

	User guardarUsuario(User user) throws IOException;
	Page<UserDto> getAllUsers(int page, String order);
	public Optional<User> findById(UUID id);
	public boolean deleteUser(UUID id);
  
	public UserResponseDto login(LoginRequestDTO loginRequestDTO);
}
