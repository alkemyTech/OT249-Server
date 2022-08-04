package com.alkemy.ong.service;

import java.util.List;
import java.util.UUID;

import com.alkemy.ong.model.Role;

public interface IRoleService {

	public Role getRoleById(String id);
	public List<Role> getAllRoles();
	public void deleteRoleById(String id);
	public void updateRole(Role role,String id);
	public Role getRoleByName(String name);
	
}