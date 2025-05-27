package com.management.students.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.*;

import com.management.students.dto.UserDTO;
import com.management.students.entity.*;
import com.management.students.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired private UserRepository userRepo;
	@Autowired private PasswordEncoder passwordEncoder;
	
	public User register(UserDTO userdto) {
		User user=new User(userdto.getUsername(),
				passwordEncoder.encode(userdto.getPassword()),
				userdto.getRole());
		
		return userRepo.save(user);
	}
	
	public User getByUsername(String username) {
		return userRepo.findByUsername(username).orElse(null);
	}
}
