package com.management.students.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(unique=true, nullable=false)
	private String username;
	@Email(message="Provide a valid email")
	private String email;
	
	@Column(nullable=false)
	private String password;
	
	
	
	@OneToOne(mappedBy="user",cascade=CascadeType.ALL)
	@JsonIgnore
	private Student student;
	
	@OneToOne(mappedBy="user",cascade=CascadeType.ALL)
	@JsonIgnore
	private Staff staff;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="role_id",nullable=false)
	private Role role;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}

