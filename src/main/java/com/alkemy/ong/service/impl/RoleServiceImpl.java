package com.alkemy.ong.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alkemy.ong.model.Role;
import com.alkemy.ong.repository.RoleRepository;
import com.alkemy.ong.service.IRoleService;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements IRoleService{

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public Role getRoleById(String id) {
		Optional<Role> roleOptional = roleRepository.findById(id);
		if (roleOptional.isPresent()) {
			return roleOptional.get();
		} else {
			return null;
		}
	}

	@Override
	public List<Role> getAllRoles() {

		return null;
	}

	@Override
	public void deleteRoleById(String id) {
		
	}

	@Override
	public void updateRole(Role role, String id) {
		
	}

	@Override
	public Role getRoleByName(String name){
		return roleRepository.findByName(name);
	}

}
