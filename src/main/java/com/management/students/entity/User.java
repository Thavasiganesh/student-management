package com.management.students.entity;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(unique=true, nullable=false)
	private String username;
	@Column(nullable=false)
	private String password;
	private String role;
	
	public User() {
		
	}
	public User( String username, String password, String role) {
		this.username = username;
		this.password = password;
		if(role!=null)
			this.role = role;
		else
			this.role="ROLE_USER";
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return username;
	}
	public void setUserName(String userName) {
		this.username = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}

