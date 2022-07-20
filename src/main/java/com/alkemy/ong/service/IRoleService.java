package com.alkemy.ong.service;

import java.util.List;
import java.util.UUID;

import com.alkemy.ong.model.Role;

public interface IRoleService {

	public Role getRoleById(UUID id);
	public List<Role> getAllRoles();
	public void deleteRoleById(UUID id);
	public void updateRole(Role role,UUID id);
	
}