package com.management.students.dto;

import java.time.LocalDate;
import java.util.Set;

public class StudentDTO {
	

	private Long id;
	
	private String name;
	
	private String departmentName;
	private String email;
	private String phone;
	private LocalDate dob;
	private int enrollmentYear;
	private Set<String> courseNames;
	public StudentDTO() {}
	public StudentDTO(Long id, String name, String departmentName, String email, String phone, LocalDate dob,
			int enrollmentYear, Set<String> courseNames) {
		this.id = id;
		this.name = name;
		this.departmentName = departmentName;
		this.email = email;
		this.phone = phone;
		this.dob = dob;
		this.enrollmentYear = enrollmentYear;
		this.courseNames = courseNames;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
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
	public LocalDate getDob() {
		return dob;
	}
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	public int getEnrollmentYear() {
		return enrollmentYear;
	}
	public void setEnrollmentYear(int enrollmentYear) {
		this.enrollmentYear = enrollmentYear;
	}
	public Set<String> getCourseNames() {
		return courseNames;
	}
	public void setCourseNames(Set<String> courseNames) {
		this.courseNames = courseNames;
	}
	
	
}
