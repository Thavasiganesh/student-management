package com.management.students.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name="enrollments")
public class Enrollment {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    private LocalDate enrollmentDate;
    
    public Enrollment() {}
    
    public Enrollment(Long id, Student student, Course course, LocalDate enrollmentDate) {
		this.id = id;
		this.student = student;
		this.course = course;
		this.enrollmentDate = enrollmentDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public LocalDate getEnrollmentDate() {
		return enrollmentDate;
	}

	public void setEnrollmentDate(LocalDate enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}

	

}
