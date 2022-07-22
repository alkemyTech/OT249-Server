package com.alkemy.ong.service;

import java.util.UUID;

import com.alkemy.ong.model.User;

public interface UserService {

	public User guardarUsuario(User user);
	public User findById(UUID id);
}
