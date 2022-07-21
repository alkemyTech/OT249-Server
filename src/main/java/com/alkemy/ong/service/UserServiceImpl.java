package com.alkemy.ong.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alkemy.ong.model.User;
import com.alkemy.ong.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	@Transactional
	public User guardarUsuario(User user) {
		return userRepo.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

		return userRepo.findByEmail( s ).orElseThrow(() -> new UsernameNotFoundException( "NOT FOUND" ));
	}
}
