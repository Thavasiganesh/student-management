package com.management.students.dto;

import java.util.List;
import java.util.Set;

import com.management.students.entity.Course;
import com.management.students.entity.Department;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class StudentDTO {
	@NotBlank(message="Name cannnot be empty")
	private String name;
	
	@NotBlank(message="Provide a department")
	private Department department;
	
	@Email(message="Provide a valid email")
	private String email;
	private String phone;
	private Set<Course> courses;
	public StudentDTO( String name,
			 Department department,
			 String email, String phone, Set<Course> courses) {
		this.name = name;
		this.department = department;
		this.email = email;
		this.phone = phone;
		this.courses = courses;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Set<Course> getCourses() {
		return courses;
	}
	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}
	
}
