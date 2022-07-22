package com.alkemy.ong.service;

import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.model.User;
import org.springframework.data.domain.Page;

public interface UserService {

	User guardarUsuario(User user);
	Page<UserDto> getAllUsers(int page, String order);

}
