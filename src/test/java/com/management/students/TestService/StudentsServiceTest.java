package com.management.students.TestService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.management.students.dto.StudentDTO;
import com.management.students.entity.Student;
import com.management.students.repository.StudentRepository;
import com.management.students.service.StudentService;


@SpringBootTest
public class StudentsServiceTest {
	@MockBean
	private StudentRepository studentRepository;
	
	@Autowired
	private StudentService studentService;
	
	@Test
	void testCreateStudent() {
		Student student = new Student("Thavasi","MCA","thavasi@gmail",22);
		Mockito.when(studentRepository.save(Mockito.any(Student.class))).thenReturn(student);
		StudentDTO stud=new StudentDTO(student.getName(),student.getDepartment()
				,student.getEmail(),student.getAge());
		StudentDTO saved=studentService.addStudent(stud);
		Assertions.assertEquals("Thavasi",saved.getName());
	}

}
