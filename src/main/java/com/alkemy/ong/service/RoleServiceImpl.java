package com.alkemy.ong.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alkemy.ong.model.Role;
import com.alkemy.ong.repository.RoleRepository;

@Service
public class RoleServiceImpl implements IRoleService{

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public Role getRoleById(UUID id) {
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
	public void deleteRoleById(UUID id) {
		
	}

	@Override
	public void updateRole(Role role, UUID id) {
		
	}

}
