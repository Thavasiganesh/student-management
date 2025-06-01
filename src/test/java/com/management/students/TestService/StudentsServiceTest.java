package com.management.students.TestService;

import java.time.LocalDate;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.management.students.dto.StudentDTO;
import com.management.students.entity.Department;
import com.management.students.entity.Student;
import com.management.students.repository.StudentRepository;
import com.management.students.service.StudentService;
import com.management.students.service.StudentServiceImpl;


@SpringBootTest
public class StudentsServiceTest {
	@MockBean
	private StudentRepository studentRepository;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	StudentServiceImpl serviceImpl;
	
	@Test
	void testCreateStudent() {

		Student student = new Student();
		student.setName("Thavasi");
		student.setEmail("thavasiganesh02@gmail.com");
		student.setDob(LocalDate.of(2003,10,04));
		student.setEnrollmentYear(2021);
		student.setPhone("9543395034");
		Department department=new Department();
		department.setName("MCA");
		department.setHeadOfDepartment("Rajesh");
		student.setDepartment(department);
		
		Mockito.when(studentRepository.save(Mockito.any(Student.class))).thenReturn(student);
		StudentDTO stud=new StudentDTO();
		stud.setName(student.getName());
		stud.setEmail(student.getEmail());
		stud.setDob(student.getDob());
		stud.setEnrollmentYear(student.getEnrollmentYear());
		stud.setPhone(student.getPhone());
		stud.setDepartmentName(student.getDepartment().getName());
		StudentDTO saved=serviceImpl.addStudent(stud);
		Assertions.assertEquals("Thavasi",saved.getName());
	}

}
