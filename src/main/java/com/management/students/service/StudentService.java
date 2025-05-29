package com.management.students.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.management.students.dto.StudentDTO;
import com.management.students.dto.StudentPageResponse;
import com.management.students.dto.StudentPatchDTO;
import com.management.students.dto.StudentsListWrapper;
import com.management.students.entity.Student;


public interface StudentService {
	StudentDTO addStudent(StudentDTO stud);
	List<StudentDTO> addStudents(StudentsListWrapper listOfStuds);
	ResponseEntity<String> deleteStudent(Long id);
	StudentPageResponse getAllStudents(int page,int size,String sortBy,String direction);
	ResponseEntity<?> getStudentById(Long id);

	ResponseEntity<String> restoreDeletedStudent(Long id);
	List<StudentDTO> searchByDepartment(String department);
	List<StudentDTO> searchByName(String name);
	ResponseEntity<?> studentPartialUpdate(Long id,StudentPatchDTO stud);
	
}
