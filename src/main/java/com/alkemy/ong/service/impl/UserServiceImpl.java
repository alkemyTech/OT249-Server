package com.alkemy.ong.service.impl;


import com.alkemy.ong.Utils.JwtUtil;
import com.alkemy.ong.Utils.PageUtils;
import com.alkemy.ong.dto.LoginRequestDTO;
import com.alkemy.ong.dto.RoleDto;
import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.dto.UserResponseDto;
import com.alkemy.ong.model.User;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.EmailService;
import com.alkemy.ong.service.UserService;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private EmailService emailService;

	@Autowired
    private AuthenticationManager authenticationManager;

	@Autowired
	JwtUtil jwtUtil;
	
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

	@Override
	public Optional<User> findById(String id) {
		return userRepo.findById(id);
	}

	@Override
	@Transactional
	public boolean deleteUser(String id) {
		try {
			userRepo.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public UserResponseDto login(LoginRequestDTO loginRequestDTO) {
		try {
			Authentication authentication = authenticationManager
					.authenticate(
							new UsernamePasswordAuthenticationToken(
									loginRequestDTO.getEmail(),
									loginRequestDTO.getPassword()));
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			return new UserResponseDto(jwtUtil.generateToken(userDetails));
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Email o contraseña incorrecta ", e);
		}
	}
}
