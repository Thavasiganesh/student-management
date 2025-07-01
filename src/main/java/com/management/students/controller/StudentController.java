package com.management.students.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;

import com.management.students.dto.*;
import com.management.students.service.CustomUserDetails;
import com.management.students.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/students")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	private static final Logger logger=LoggerFactory.getLogger(StudentController.class);
	
	@PreAuthorize("hasRole('STUDENT')")
	@PostMapping
	public StudentDTO createStudent(@RequestBody @Valid ProfileDTO student,@AuthenticationPrincipal CustomUserDetails userDetails  ) {
		logger.info("Creating a student profile:{}",userDetails.getUsername());
		return studentService.addStudent(student,userDetails);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/bulk")
	public List<StudentDTO> addStudents(@RequestBody @Valid StudentsListWrapper studentsList){
		logger.info("Adding multiple students:{}");
		return studentService.addStudents(studentsList);
	}
	@PreAuthorize("hasAnyRole('STUDENT','ADMIN')")
	@PostMapping("/{id}/courses")
	public ResponseEntity<?> erollStudentInCourses(@PathVariable Long studId,@RequestBody List<Long> courseIds){
		studentService.enrollInCourses(studId, courseIds);
		return ResponseEntity.ok("Student enrolled in courses successfully");
	}
//	
//	
	@PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
	@PatchMapping("/{id}")
	public ResponseEntity<?> studentPartialUpdate(@PathVariable Long id,@RequestBody  StudentPatchDTO stud,@AuthenticationPrincipal CustomUserDetails userDetails){
		logger.info("Updating a specific detail of a student with id:{}",stud.getName());
		return studentService.studentPartialUpdate(id, stud,userDetails);
	}
//	
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/restore/{id}")
	public ResponseEntity<String> restoreDeletedStudent(@PathVariable Long id){
		logger.info("Restoring a deleted student using id:{}",id);
		return studentService.restoreDeletedStudent(id);
		
	}
//	
	@PreAuthorize("hasAnyRole('STAFF','ADMIN')")
	@GetMapping
	public StudentPageResponse getAllStudents(@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="5") int size,@RequestParam(defaultValue="id") String sortBy,@RequestParam(defaultValue="asc") String direction){
		logger.info("Retrieving all students in pages with a specific number of items sorted by specific field in asc or desc :{}",page,size,sortBy,direction);
		return studentService.getAllStudents(page, size, sortBy, direction);
	}
//	
	@PreAuthorize("hasAnyRole('STAFF','ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getStudentById(@PathVariable Long id){
		logger.info("Retrieving a specific student with id:{}",id);
		return studentService.getStudentById(id);
	}
//	
	@PreAuthorize("hasAnyRole('STAFF','ADMIN')")
	@GetMapping("/search/byName")
	public List<StudentDTO> searchByName(@RequestParam String name){
		logger.info("Retrieving students by their name:{}",name);
		return studentService.searchByName(name);
	}
//	
	@PreAuthorize("hasAnyRole('STAFF','ADMIN')")
	@GetMapping("/search/byDepartment")
	public List<StudentDTO> searchByDepartment(@RequestParam String department){
		logger.info("Retrieving students by their department:{}",department);
		return studentService.searchByDepartment(department);
	}
	
	@PreAuthorize("hasRole('STUDENT')")
	@GetMapping("/{id}/courses")
	public List<CourseDTO> getAllCourses(@PathVariable Long studId){
		
		return studentService.getAllCourses(studId);
		
	}
	
	@PreAuthorize("hasRole('STUDENT')")
	@GetMapping("/me")
	public ResponseEntity<?> checkStudentProfile(@AuthenticationPrincipal CustomUserDetails userDetails){
		return studentService.checkStudentProfile(userDetails);
	}
//	
	@PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
		logger.warn("Deleting a specific student with id:{}",id);
		return studentService.deleteStudent(id);
	}
	
}
