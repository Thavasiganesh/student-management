package com.management.students.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name="exams")
public class Exam {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String examTitle;

    private LocalDate examDate;

    private String classroom;

    private String duration;

    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;
    
    @OneToMany(mappedBy="exam",cascade=CascadeType.ALL)
    @JsonIgnore
    private  List<ExamResult> examResults;
    
	public Exam() {
	}
	

	public Exam(Long id, String examTitle, Course course, LocalDate examDate, String classroom, String duration) {
		this.id = id;
		this.examTitle = examTitle;
		this.course = course;
		this.examDate = examDate;
		this.classroom = classroom;
		this.duration = duration;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExamTitle() {
		return examTitle;
	}

	public void setExamTitle(String examTitle) {
		this.examTitle = examTitle;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public LocalDate getExamDate() {
		return examDate;
	}

	public void setExamDate(LocalDate examDate) {
		this.examDate = examDate;
	}

	public String getClassroom() {
		return classroom;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

}
