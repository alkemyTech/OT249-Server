package com.alkemy.ong.service.impl;


import com.alkemy.ong.Utils.PageUtils;
import com.alkemy.ong.dto.RoleDto;
import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.model.User;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.EmailService;
import com.alkemy.ong.service.UserService;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private EmailService emailService;
	
	@Override
	@Transactional
	public User guardarUsuario(User user) throws IOException {

		User userSaved = userRepo.save(user);

		emailService.WelcomeMail(userSaved.getEmail(), user.getFirstName());

		return userSaved;
	}

	@Override
	public Page<UserDto> getAllUsers(int page, String order) {

		Page<User> users = userRepo.findAll( PageUtils.getPageable( page, order ) );
		return users.map( user -> modelMapper.map( user, UserDto.class ) );
	}

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		User found = userRepo.findByEmail( s ).orElseThrow( () -> new UsernameNotFoundException( "NOT FOUND" ) );
		UserDto userDto = modelMapper.map( found, UserDto.class );
		userDto.setRole( modelMapper.map(found.getRole(), RoleDto.class  ) );
		return userDto;
	}

	// @Override
	// public User findById(UUID id) {
	// 	return userRepo.findByUserId(id);
	// }

	@Override
	@Transactional
	public boolean deleteUser(UUID id) {
		try {
			userRepo.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}