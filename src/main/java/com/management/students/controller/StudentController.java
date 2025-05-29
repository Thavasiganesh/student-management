package com.management.students.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import com.management.students.dto.*;
import com.management.students.entity.Student;
import com.management.students.repository.StudentRepository;
import com.management.students.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/students")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	private static final Logger logger=LoggerFactory.getLogger(StudentController.class);
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping
	public Student createStudent(@RequestBody @Valid Student student ) {
		logger.info("Adding a student:{}",student.getName());
		return studentService.addStudent(student);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/bulk")
	public List<Student> addStudents(@RequestBody @Valid StudentsListWrapper studentsList){
		logger.info("Adding multiple students:{}");
		return studentService.addStudents(studentsList);
	}
//	
//	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PatchMapping("/{id}")
	public ResponseEntity<?> studentPartialUpdate(@PathVariable Long id,@RequestBody  StudentPatchDTO stud){
		logger.info("Updating a specific detail of a student with id:{}",stud.getName());
		return studentService.studentPartialUpdate(id, stud);
	}
//	
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/restore/{id}")
	public ResponseEntity<String> restoreDeletedStudent(@PathVariable Long id){
		logger.info("Restoring a deleted student using id:{}",id);
		return studentService.restoreDeletedStudent(id);
		
	}
//	
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@GetMapping
	public StudentPageResponse getAllStudents(@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="5") int size,@RequestParam(defaultValue="id") String sortBy,@RequestParam(defaultValue="asc") String direction){
		logger.info("Retrieving all students in pages with a specific number of items sorted by specific field in asc or desc :{}",page,size,sortBy,direction);
		return studentService.getAllStudents(page, size, sortBy, direction);
	}
//	
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getStudentById(@PathVariable Long id){
		logger.info("Retrieving a specific student with id:{}",id);
		return studentService.getStudentById(id);
	}
//	
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@GetMapping("/search/byName")
	public List<Student> searchByName(@RequestParam String name){
		logger.info("Retrieving students by their name:{}",name);
		return studentService.searchByName(name);
	}
//	
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@GetMapping("/search/byDepartment")
	public List<Student> searchByDepartment(@RequestParam String department){
		logger.info("Retrieving students by their department:{}",department);
		return studentService.searchByDepartment(department);
	}
//	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
		logger.warn("Deleting a specific student with id:{}",id);
		return studentService.deleteStudent(id);
	}
	
}
