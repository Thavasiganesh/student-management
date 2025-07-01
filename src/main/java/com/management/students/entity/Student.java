package com.management.students.entity;

import java.time.LocalDate;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="students")
public class Student {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long Id;
	@NotBlank(message="Name cannnot be empty")
	private String name;

	@Email(message="Provide a valid email")
	private String email;
	@JsonIgnore
	private Boolean isDeleted=false;
	@PastOrPresent
	private LocalDate dob;
	
	private int enrollmentYear;
	
	private String phone;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="department_id")
	private Department department;
	
	@ManyToMany
	@JoinTable(
			name="student_courses",
			joinColumns=@JoinColumn(name="student_id"),
			inverseJoinColumns=@JoinColumn(name="course_id")
			)
	private Set<Course> courses=new HashSet<>();
	
	@OneToMany(mappedBy="student",cascade=CascadeType.ALL)
	@JsonIgnore
	private List<Enrollment> enrollments;
	
	@OneToMany(mappedBy="student",cascade=CascadeType.ALL)
	@JsonIgnore
	private List<ExamResult> examResults;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
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

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}
	
}
