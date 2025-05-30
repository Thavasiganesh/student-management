package com.management.students.service;

import java.util.List;

import com.management.students.dto.RoleDTO;

public interface RoleService {
	
	List<RoleDTO> getAllRoles();
	RoleDTO getRoleById(Long id);

}
