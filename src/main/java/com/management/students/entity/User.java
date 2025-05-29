package com.management.students.entity;

import java.util.*;

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
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name="user_roles",
			joinColumns=@JoinColumn(name="user_id"),
			inverseJoinColumns=@JoinColumn(name="role_id")
			)
	private Set<Role> roles=new HashSet<>();
	
	
}

