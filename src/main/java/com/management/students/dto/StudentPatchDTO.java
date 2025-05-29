package com.management.students.dto;

import jakarta.validation.constraints.*;

public class StudentPatchDTO {
	
	@NotBlank(message="Name cannot be empty")
	private String name;

	@Email(message="Invalid Email format")
	private String email;
	private String phone;

	
	public StudentPatchDTO(String name, String email, String phone) {
		this.name = name;
		this.email = email;
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
}
