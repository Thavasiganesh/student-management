package com.management.students.entity;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;


@Entity
@Table(name="departments")

public class Department {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(nullable=false,unique=true)
	private String name;
	
	private String headOfDepartment;
	
	@OneToMany(mappedBy="department",cascade=CascadeType.ALL )
	@JsonIgnore
	private List<Course> courses;
	
	@OneToMany(mappedBy="department",cascade=CascadeType.ALL)
	private List<Student> students;
	
	@OneToMany(mappedBy="department",cascade=CascadeType.ALL)
	private List<Staff> staffs;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeadOfDepartment() {
		return headOfDepartment;
	}

	public void setHeadOfDepartment(String headOfDepartment) {
		this.headOfDepartment = headOfDepartment;
	}
	


}
