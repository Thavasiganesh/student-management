package com.management.students.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;


@Entity
@Table(name="courses")
public class Course {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String courseCode;
	
	@Column(nullable=false)
	private String courseName;
	
	@Column(length=500)
	private String description;
	
	private int credits;
	
	@ManyToOne
	@JoinColumn(name="department_id")
	private Department department;
	
	@ManyToMany(mappedBy="courses")
	@JsonIgnore
	private Set<Student> students=new HashSet<>();
	
	@OneToMany(mappedBy="course", cascade=CascadeType.ALL)
	@JsonIgnore
	private List<Exam> exams;
	
	@OneToMany(mappedBy="course",cascade=CascadeType.ALL)
	@JsonIgnore
	private List<Enrollment> enrollments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}



}
