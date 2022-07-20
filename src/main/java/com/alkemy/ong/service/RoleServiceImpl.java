package com.alkemy.ong.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.alkemy.ong.model.Role;

@Service
public class RoleServiceImpl implements IRoleService{

	@Override
	public Role getRoleById(UUID id) {
		
		return null;
	}

	@Override
	public List<Role> getAllRoles() {

		return null;
	}

	@Override
	public void deleteRoleById(Long id) {
		
	}

	@Override
	public void updateRole(Role role, Long id) {
		
	}

}
