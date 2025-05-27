package com.management.students.dto;

import java.util.List;

import com.management.students.entity.Student;

import jakarta.validation.*;
import jakarta.validation.constraints.*;

public class StudentsListWrapper {
	
	@NotEmpty(message="Not a single record is given to insert")
	@Valid
	private List<Student> students;
	public StudentsListWrapper() {
		
	}
	
	public StudentsListWrapper(List<Student> students) {
		this.students=students;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}
	
	
	
}
