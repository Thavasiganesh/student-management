package com.management.students.service;

import java.util.*;
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

import com.management.students.dto.CourseDTO;
import com.management.students.dto.ProfileDTO;
import com.management.students.dto.StudentDTO;
import com.management.students.dto.StudentPageResponse;
import com.management.students.dto.StudentPatchDTO;
import com.management.students.dto.StudentsListWrapper;
import com.management.students.entity.Course;
import com.management.students.entity.Department;
import com.management.students.entity.Role;
import com.management.students.entity.Student;
import com.management.students.entity.User;
import com.management.students.exception.AccessDeniedException;
import com.management.students.exception.ResourceNotFoundException;
import com.management.students.repository.CourseRepository;
import com.management.students.repository.DepartmentRepository;
import com.management.students.repository.StudentRepository;
import com.management.students.repository.UserRepository;


@Service
public class StudentServiceImpl implements StudentService {

	private final StudentRepository studentRepository;
	
	@Autowired
	private EmailService emailService;
	
	private final DepartmentRepository departmentRepository;
	
	private final CourseRepository courseRepository;
	
	private final UserRepository userRepository;

	public StudentServiceImpl(StudentRepository studentRepository, DepartmentRepository departmentRepository,
			CourseRepository courseRepository,UserRepository userRepository) {
		this.studentRepository = studentRepository;
		this.departmentRepository = departmentRepository;
		this.courseRepository = courseRepository;
		this.userRepository = userRepository;
	}
	
	private Student convertToStudent(ProfileDTO stud,CustomUserDetails userDetails) {
		User user=userRepository.findByEmail(userDetails.getUsername()).orElseThrow(()->new ResourceNotFoundException("User not registered with name "));
		
		Student s=new Student();
		s.setName(user.getUsername());
		s.setEmail(user.getEmail());
		s.setDob(stud.getDob());
		s.setEnrollmentYear(stud.getEnrollmentYear());
		s.setPhone(stud.getPhone());
		s.setUser(user);
		Optional<Department> optDepartment=departmentRepository.findByName(stud.getDepartmentName());
		if(optDepartment.isEmpty()) {
			throw new ResourceNotFoundException("Department not found with name "+stud.getDepartmentName());
		}
		s.setDepartment(optDepartment.get());
		if(stud.getCourseNames()!=null && !stud.getCourseNames().isEmpty()) {
			Set<Course> courses=new HashSet<>();
			for(String courseName:stud.getCourseNames()) {
				Course course=courseRepository.findByCourseName(courseName).orElseThrow(()->new
						ResourceNotFoundException("Course not found with name "+courseName));
				courses.add(course);
			}
			s.setCourses(courses);
		}
		return s;
	}
	
	private Student convertToStudent(StudentDTO stud) { 
		
		Student s=new Student();
		s.setName(stud.getName());
		s.setEmail(stud.getEmail());
		s.setDob(stud.getDob());
		s.setEnrollmentYear(stud.getEnrollmentYear());
		s.setPhone(stud.getPhone());
		Optional<Department> optDepartment=departmentRepository.findByName(stud.getDepartmentName());
		if(optDepartment.isEmpty()) {
			throw new ResourceNotFoundException("Department not found with name "+stud.getDepartmentName());
		}
		s.setDepartment(optDepartment.get());
		if(stud.getCourseNames()!=null && !stud.getCourseNames().isEmpty()) {
			Set<Course> courses=new HashSet<>();
			for(String courseName:stud.getCourseNames()) {
				Course course=courseRepository.findByCourseName(courseName).orElseThrow(()->new
						ResourceNotFoundException("Course not found with name "+courseName));
				courses.add(course);
			}
			s.setCourses(courses);
		}
		return s;
	}
//	
	private StudentDTO convertToStudentDTO(Student stud) {
		Set<Course> courses= stud.getCourses();
		Set<String> coursesNames=new HashSet<>();
		for(Course course:courses) {
			coursesNames.add(course.getCourseName());
		}
		StudentDTO s=new StudentDTO(stud.getId(),stud.getName(),stud.getDepartment().getName()
				,stud.getEmail(),stud.getPhone(),stud.getDob(),stud.getEnrollmentYear(),coursesNames);
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
		emailService.sendEmail(std.getEmail(), "Student profile deleted from the system", "Student "+std.getName()+" was deleted");
		return ResponseEntity.ok("Student profile deleted successfully");
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
		
		return ResponseEntity.ok(convertToStudentDTO(std));
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
	public ResponseEntity<?> studentPartialUpdate(Long id, StudentPatchDTO stud, CustomUserDetails userDetails) {
		// TODO Auto-generated method stub
		if(userDetails.getRole("STUDENT") && !Objects.equals(id,stud.getId() )) {
			throw new AccessDeniedException("You can only edit your own profile");
		}
		Optional<Student> optStud=studentRepository.findById(id);
		if(optStud.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found with id "+id);
		}
		
		Student std=optStud.get();
		Optional<User> optUser=userRepository.findByUsername(std.getName()); 
		User user=optUser.get();
		if(stud.getName()!=null) {
			std.setName(stud.getName());
			user.setUsername(stud.getName());
		}

		if(stud.getEmail()!=null) {
			std.setEmail(stud.getEmail());
			user.setEmail(stud.getEmail());
		}
		
		if (stud.getPhone()!=null) {
			std.setPhone(stud.getPhone());
		}
		
		userRepository.save(user);
		studentRepository.save(std);
		
		return ResponseEntity.ok("Provided student details updated successfully");
	}
	
	//Written only for StudentServiceTest purpose 
	public StudentDTO addStudent(StudentDTO studDTO) {
		// TODO Auto-generated method stub
		Student stud=convertToStudent(studDTO);
		studentRepository.save(stud);
//		emailService.sendEmail(stud.getEmail(), "Student added", "Student "+stud.getName()+" was added");
		return convertToStudentDTO(stud);
	}

	@Override
	public StudentDTO addStudent(ProfileDTO studDTO,CustomUserDetails userDetails) {
		// TODO Auto-generated method stub
		Student stud=convertToStudent(studDTO,userDetails);
		studentRepository.save(stud);
		emailService.sendEmail(stud.getEmail(), "Student added", "Student "+stud.getName()+" was added");
		return convertToStudentDTO(stud);
	}

	@Override
	public List<StudentDTO> addStudents(StudentsListWrapper listOfStuds) {
		// TODO Auto-generated method stub
		List<Student> s=listOfStuds.getStudents();
		studentRepository.saveAll(s);
		
		Role role=new Role();
		role.setId((long) 3);
		role.setName("ROLE_STUDENT");
		for(Student stud:s) {
			User user=new User();
			user.setEmail(stud.getEmail());
			user.setPassword(stud.getDob().toString());
			user.setUsername(stud.getName());
			user.setRole(role);
			userRepository.save(user);
		}
		
		return convertListOfStudsToStudsDTO(s);
	}


	@Override
	public List<StudentDTO> searchByDepartment(String department) {
		// TODO Auto-generated method stub
		Optional<Department> valid=departmentRepository.findByName(department);
		if(valid.isEmpty())
			throw new ResourceNotFoundException("Department not found with name "+department);
		
		List<Student> list=studentRepository.findByDepartmentNameContainingIgnoreCase(valid.get().getName());
		return convertListOfStudsToStudsDTO(list);
	}

	@Override
	public List<StudentDTO> searchByName(String name) {
		// TODO Auto-generated method stub
		List<Student> list=studentRepository.findByNameContainingIgnoreCase(name);
		
		return convertListOfStudsToStudsDTO(list);
	}

	@Override
	public void enrollInCourses(Long id, List<Long> courseIds) {
		// TODO Auto-generated method stub
		Student student=studentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Student not found"));
		List<Course> courses=courseRepository.findAllById(courseIds);
		student.getCourses().addAll(courses);
		studentRepository.save(student);
	}

	@Override
	public List<CourseDTO> getAllCourses(Long id) {
		// TODO Auto-generated method stub
		Student student=studentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Student not found"));
		Set<Course> courses=student.getCourses();
		List<CourseDTO> resultCourses=new ArrayList<CourseDTO>();
		for(Course course: courses) {
			CourseDTO courseDTO=new CourseDTO();
			courseDTO.setId(course.getId());
			courseDTO.setName(course.getCourseName());
			courseDTO.setCode(course.getCourseCode());
			courseDTO.setCredits(course.getCredits());
			courseDTO.setDescription(course.getDescription());
			resultCourses.add(courseDTO);
		}
		return resultCourses;
	}

	@Override
	public ResponseEntity<?> checkStudentProfile(CustomUserDetails userDetails) {
		// TODO Auto-generated method stub
		Optional<Student> student=studentRepository.findByEmail(userDetails.getUsername());
		return ResponseEntity.ok(Map.of("hasProfile", student.isPresent()));
	}


}
