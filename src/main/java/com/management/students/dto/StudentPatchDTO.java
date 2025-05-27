package com.management.students.dto;

import jakarta.validation.constraints.*;

public class StudentPatchDTO {
	
	@NotBlank(message="Name cannot be empty")
	private String name;
	@NotBlank(message="Department cannot be empty")
	private String department;
	@Email(message="Invalid Email format")
	private String email;
	@Min(value=3,message="Age must be greater than 3")
	private Integer age;
	public StudentPatchDTO(String name, String department, String email, Integer age) {
		this.name = name;
		this.department = department;
		this.email = email;
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
}
