package com.management.students.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.management.students.dto.*;
import com.management.students.entity.Staff;
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
	
	@Autowired
	private StaffService staffService;
	
	@Autowired
	private UserRepository userRepository;
	
	private static final Logger logger=LoggerFactory.getLogger(AuthController.class);
	
	@PostMapping("/register")
	public String register(@RequestBody @Valid UserDTO userdto){
		logger.info("Registering new user:{}",userdto.getUsername());
		userService.register(userdto);
		return "User registered successfully";
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest request) {
		logger.info("Login attempt for user:{}",request.getEmail());
		try{
		authManager.authenticate(new UsernamePasswordAuthenticationToken(
				request.getEmail(),request.getPassword()));
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
		}
		String token = jwtService.generateToken(request.getEmail());
		logger.info("Login successful for user:{}",request.getEmail());
		return ResponseEntity.ok(new AuthResponse(token));
	}
	
	@DeleteMapping("/{email}")
	public ResponseEntity<String> delete(@PathVariable String email){
		Optional<User> user=userRepository.findByEmail(email);
		if(user.isEmpty())
			return ResponseEntity.notFound().build();
		userRepository.delete(user.get());
		return ResponseEntity.ok("User successfully deleted.");	
	}
}
