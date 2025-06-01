package com.management.students.dto;

import java.time.LocalDate;
import java.util.Set;

public class ProfileDTO {
	
	private LocalDate dob;
	
	private String phone;
	
	private int enrollmentYear;
	
	private String departmentName;
	
	private Set<String> courseNames;

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getEnrollmentYear() {
		return enrollmentYear;
	}

	public void setEnrollmentYear(int enrollmentYear) {
		this.enrollmentYear = enrollmentYear;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Set<String> getCourseNames() {
		return courseNames;
	}

	public void setCourseNames(Set<String> courseNames) {
		this.courseNames = courseNames;
	}
	
	
	

}
