package com.management.students.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.management.students.dto.RoleDTO;
import com.management.students.entity.Role;
import com.management.students.exception.ResourceNotFoundException;
import com.management.students.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {
	
	private final RoleRepository roleRepository;
	
	

	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public List<RoleDTO> getAllRoles() {
		// TODO Auto-generated method stub
		List<RoleDTO> result=roleRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
		return result;
	}

	@Override
	public RoleDTO getRoleById(Long id) {
		// TODO Auto-generated method stub
		Optional<Role> role=roleRepository.findById(id);
		if(role.isPresent())
			return convertToDTO(role.get());
		throw new ResourceNotFoundException("Role not found with id "+id);
	}
	
	private RoleDTO convertToDTO(Role role) {
		RoleDTO roleDto=new RoleDTO();
		roleDto.setId(role.getId());
		roleDto.setName(role.getName());
		return roleDto;
	}

}
