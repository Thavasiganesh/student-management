package com.management.students.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.management.students.dto.StudentDTO;
import com.management.students.dto.StudentPageResponse;
import com.management.students.dto.StudentPatchDTO;
import com.management.students.dto.StudentsListWrapper;
import com.management.students.entity.Student;
import com.management.students.repository.StudentRepository;

import jakarta.validation.Valid;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private EmailService emailService; 

	private Student convertToStudent(StudentDTO stud) {
		Student s=new Student(stud.getName(),stud.getDepartment()
				,stud.getEmail(),stud.getAge());
		return s;
	}
	
	private StudentDTO convertToStudentDTO(Student stud) {
		StudentDTO s=new StudentDTO(stud.getName(),stud.getDepartment()
				,stud.getEmail(),stud.getAge());
		return s;
	}
	
	private List<StudentDTO> convertListOfStudsToStudsDTO(List<Student> students){
		List<StudentDTO> studs = new ArrayList<StudentDTO>();
//		s.forEach(stud->convertToStudentDTO(stud).);
		for(Student stud:students) {
			studs.add(convertToStudentDTO(stud));
		}
		return studs;
	}
	
	@Override
	public ResponseEntity<String> deleteStudent(Long id) {
		// TODO Auto-generated method stub
		Optional<Student> deletedStd=studentRepository.findById(id);
		if(deletedStd.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found with id "+id);
		Student std=deletedStd.get();
		std.setIsDeleted(true);
		studentRepository.save(std);
		emailService.sendEmail(std.getEmail(), "Student deleted from the system", "Student "+std.getName()+" was deleted");
		return ResponseEntity.ok("Student deleted successfully");
	}

	@Override
	public StudentPageResponse getAllStudents(int page, int size, String sortBy, String direction) {
		Sort sort=direction.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		Pageable pages=PageRequest.of(page, size, sort);
		Page<Student> studentPage=studentRepository.findByIsDeletedFalse(pages);
		return new StudentPageResponse(
				studentPage.getContent(),studentPage.getNumber(),studentPage.getTotalPages(),
				studentPage.getTotalElements(),sortBy,direction
				);
	}

	@Override
	public ResponseEntity<?> getStudentById(Long id) {
		// TODO Auto-generated method stub
		Optional<Student> stud=studentRepository.findByIdAndIsDeletedFalse(id);
		if(stud.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student is not found with id "+id);
		Student std=stud.get(); 
		return ResponseEntity.ok(new StudentDTO(std.getName(),
				std.getDepartment(),std.getEmail(),std.getAge()));
	}

	@Override
	public ResponseEntity<String> restoreDeletedStudent(Long id) {
		// TODO Auto-generated method stub
		return studentRepository.findById(id).map(std->{
			std.setIsDeleted(false);
			studentRepository.save(std);
			return ResponseEntity.ok("Student restored successfully");
		}).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found with id "+id));
	}

	@Override
	public ResponseEntity<?> studentPartialUpdate(Long id, StudentPatchDTO stud) {
		// TODO Auto-generated method stub
		Optional<Student> optStud=studentRepository.findById(id);
		if(optStud.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found with id "+id);
		}
		Student std=optStud.get();
		if(stud.getName()!=null)
			std.setName(stud.getName());
		if(stud.getAge()!=null)
			std.setAge(stud.getAge());
		if(stud.getDepartment()!=null)
			std.setDepartment(stud.getDepartment());
		if(stud.getEmail()!=null)
			std.setEmail(stud.getEmail());
		
		studentRepository.save(std);
		
		return ResponseEntity.ok("Provided student details updated successfully");
	}

	@Override
	public StudentDTO addStudent(StudentDTO stud) {
		// TODO Auto-generated method stub
		
		Student student=new Student(stud.getName(),stud.getDepartment()
				,stud.getEmail(),stud.getAge());
		studentRepository.save(student);
		emailService.sendEmail(stud.getEmail(), "Student added", "Student "+stud.getName()+" was added");
		return convertToStudentDTO(student);
	}

	@Override
	public List<StudentDTO> addStudents(StudentsListWrapper listOfStuds) {
		// TODO Auto-generated method stub
		List<Student> s=listOfStuds.getStudents();
		studentRepository.saveAll(s);
		return convertListOfStudsToStudsDTO(s);
	}

	@Override
	public StudentDTO modifyStudent(Long id, StudentDTO student) {
		// TODO Auto-generated method stub
		Optional<Student> stud=studentRepository.findById(id);
		if(stud.isPresent()) {
			Student std=stud.get();
			std.setAge(student.getAge());
			std.setDepartment(student.getDepartment());
			std.setEmail(student.getEmail());
			std.setName(student.getName());
			studentRepository.save(std);
			return  convertToStudentDTO(std);
		}
		else {
			throw new RuntimeException("Student not found with id "+id);
		}
	}

	@Override
	public List<StudentDTO> searchByDepartment(String department) {
		// TODO Auto-generated method stub
		List<Student> list=studentRepository.findByDepartmentContainingIgnoreCase(department);
		return convertListOfStudsToStudsDTO(list);
	}

	@Override
	public List<StudentDTO> searchByName(String name) {
		// TODO Auto-generated method stub
		List<Student> list=studentRepository.findByNameContainingIgnoreCase(name);
		
		return convertListOfStudsToStudsDTO(list);
	}


}
