package com.alkemy.ong.service;

import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.model.User;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.UUID;

public interface UserService {

	User guardarUsuario(User user) throws IOException;
	Page<UserDto> getAllUsers(int page, String order);
	public User findById(UUID id);
	public boolean deleteUser(UUID id);
  
}
