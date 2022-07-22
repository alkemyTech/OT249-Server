package com.alkemy.ong.service;


import com.alkemy.ong.dto.RoleDto;
import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.model.User;
import com.alkemy.ong.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.alkemy.ong.Utils.PageUtils;
import com.alkemy.ong.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
	private final ModelMapper modelMapper;
	private final UserRepository userRepo;
	
	@Override
	@Transactional
	public User guardarUsuario(User user) {
		return userRepo.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		User found = userRepo.findByEmail( s ).orElseThrow( () -> new UsernameNotFoundException( "NOT FOUND" ) );
		UserDto userDto = modelMapper.map( found, UserDto.class );
		userDto.setRole( modelMapper.map(found.getRole(), RoleDto.class  ) );
		return userDto;
	}

	@Override
	public Page<UserDTO> getAllUsers(int page, String order) {

		Page<User> users = userRepo.findAll( PageUtils.getPageable( page, order ) );
		Page<UserDto> userDTOPage = users.map( user -> {
			return modelMapper.map( user, UserDto.class );
		} );
		return userDTOPage;
	}
}