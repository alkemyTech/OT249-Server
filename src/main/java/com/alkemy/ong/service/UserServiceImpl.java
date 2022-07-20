package com.alkemy.ong.service;


import com.alkemy.ong.Utils.PageUtils;
import com.alkemy.ong.dtos.UserDTO;
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
	public Page<UserDTO> getAllUsers(int page, String order) {

		Page<User> users = userRepo.findAll( PageUtils.getPageable( page, order ) );
		Page<UserDTO> userDTOPage = users.map( user -> {
			UserDTO userDTO = new UserDTO();
			userDTO.setEmail( user.getEmail() );
			userDTO.setFirstName( user.getFirstName() );
			userDTO.setPhoto( user.getPhoto() );
			userDTO.setLastName( user.getLastName() );
			userDTO.setTimestamp( user.getTimestamp() );
			return userDTO;
		} );
		return userDTOPage;
	}
}
