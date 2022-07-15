package com.alkemy.ong.service;

import java.util.List;

import com.alkemy.ong.model.Role;

public interface IRoleService {

	public Role getRoleById(Long id);
	public List<Role> getAllRoles();
	public void deleteRoleById(Long id);
	public void updateRole(Role role,Long id);
	
}