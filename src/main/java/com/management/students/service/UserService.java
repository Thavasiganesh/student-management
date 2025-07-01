package com.management.students.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.*;

import com.management.students.dto.UserDTO;
import com.management.students.entity.*;
import com.management.students.exception.ResourceNotFoundException;
import com.management.students.repository.RoleRepository;
import com.management.students.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired private UserRepository userRepo;
	@Autowired private PasswordEncoder passwordEncoder;
	@Autowired private RoleRepository roleRepo;
	
	public User register(UserDTO userdto) {
		User user=new User();
		user.setUsername(userdto.getUsername());
		user.setEmail(userdto.getEmail());
		user.setPassword(passwordEncoder.encode(userdto.getPassword()));
		user.setRole(roleRepo.findById(Long.parseLong(userdto.getRoleId())).orElseThrow(()->new ResourceNotFoundException("Role not found")));
		return userRepo.save(user);
	}
	
	public User getByUsername(String username) {
		return userRepo.findByUsername(username).orElse(null);
	}
}
