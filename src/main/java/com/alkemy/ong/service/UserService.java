package com.alkemy.ong.service;

import com.alkemy.ong.dtos.UserDTO;
import com.alkemy.ong.model.User;
import org.springframework.data.domain.Page;

public interface UserService {

	User guardarUsuario(User user);

    Page<UserDTO> getAllUsers(int page, String order);
}
