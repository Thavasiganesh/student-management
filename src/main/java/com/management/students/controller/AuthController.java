package com.management.students.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.management.students.dto.*;
import com.management.students.entity.User;
import com.management.students.repository.UserRepository;
import com.management.students.security.*;
import com.management.students.service.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private UserService userService;
	
	private static final Logger logger=LoggerFactory.getLogger(AuthController.class);
	
	@PostMapping("/register")
	public String register(@RequestBody @Valid UserDTO userdto){
		logger.info("Registering new user:{}",userdto.getUsername());
		userService.register(userdto);
		return "User registered successfully";
	}
	
	@PostMapping("/login")
	public AuthResponse login(@RequestBody @Valid AuthRequest request) {
		logger.info("Login attempt for user:{}",request.getUsername());
		authManager.authenticate(new UsernamePasswordAuthenticationToken(
				request.getUsername(),request.getPassword()));
		String token = jwtService.generateToken(request.getUsername());
		logger.info("Login successful for user:{}",request.getUsername());
		return new AuthResponse(token);
	}
	
//	@DeleteMapping("/{username}")
//	private ResponseEntity<String> delete(@PathVariable String username){
//		Optional<User> user=userRepository.findByUsername(username);
//		if(user.isEmpty())
//			return ResponseEntity.notFound().build();
//		userRepository.delete(user.get());
//		return ResponseEntity.ok("User successfully deleted.");	
//	}
}
