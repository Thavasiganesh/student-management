package com.management.students.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class StudentDTO {
	@NotBlank(message="Name cannnot be empty")
	private String name;
	@NotBlank(message="Provide a department")
	private String department;
	@Email(message="Provide a valid email")
	private String email;
	@Min(value=3,message="Age must be greater than 3")
	private int age;
	public StudentDTO(String name, String department, String email, int age) {

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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}
