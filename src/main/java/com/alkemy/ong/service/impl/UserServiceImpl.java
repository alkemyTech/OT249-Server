package com.alkemy.ong.service.impl;


import com.alkemy.ong.dto.*;
import com.alkemy.ong.model.User;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.EmailService;
import com.alkemy.ong.service.UserService;
import com.alkemy.ong.utils.JwtUtil;
import com.alkemy.ong.utils.PageUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
	private UserRepository userRepository;

	@Autowired
	JwtUtil jwtUtil;
	
	@Override
	@Transactional
	public User guardarUsuario(User user) {

		User userSaved = userRepo.save(user);

		emailService.WelcomeMail(userSaved.getEmail(), user.getFirstName());

		return userSaved;
	}

	@Override
	public PageDto<UserDto> getAllUsers(int page, String order) {

		Page<User> users = userRepo.findAll( PageUtils.getPageable( page, order ) );
		return PageUtils.getPageDto( users.map( user -> modelMapper.map( user, UserDto.UserPagedDto.class ) ), "users");
	}

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		User found = userRepo.findByEmail( s ).orElseThrow( () -> new UsernameNotFoundException( "NOT FOUND" ) );
		if(found.isDeleted())
			throw new UsernameNotFoundException( "NOT FOUND" );
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

	//@PreAuthorize("@userServiceImpl.validarId(#id)")
	public boolean validarId(String id) throws Exception{          
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		User user = userRepository.findByEmail(email).orElseThrow(() -> new Exception("Email no encontrado"));
		if(user.getRole().getName().equals("ADMIN")){
			return true;
		}
		return user.getId().equals(id);
	}

	public UserDto authenticatedUser() throws Exception {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String email = authentication.getName();

		User user= userRepo.findByEmail(email).orElseThrow(() -> new Exception ("User not Found"));

		return modelMapper.map(user,UserDto.class);




	}
}
