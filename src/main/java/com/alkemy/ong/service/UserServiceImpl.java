package com.alkemy.ong.service;


import com.alkemy.ong.Utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alkemy.ong.model.User;
import com.alkemy.ong.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	@Transactional
	public User guardarUsuario(User user) {
		return userRepo.save(user);
	}

	@Override
	public Page<User> getAllUsers(int page, String order) {
		return userRepo.findAll( PageUtils.getPageable(page, order) );
	}
}
