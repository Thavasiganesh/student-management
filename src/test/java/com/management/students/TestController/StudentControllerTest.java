package com.management.students.TestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.management.students.controller.StudentController;
import com.management.students.dto.StudentDTO;
import com.management.students.dto.StudentPageResponse;
import com.management.students.entity.Student;
import com.management.students.service.StudentService;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private StudentService studentService;
	
	@Test
	void testGetStudents() throws Exception{
		StudentPageResponse mockList=new StudentPageResponse(List.of(new Student("Thavasi","MCA","thavasi@gmail",22)));
		Mockito.when(studentService.getAllStudents(0, 0,"name", "desc")
				).thenReturn(mockList);
		mockMvc.perform(get("/students")).andExpect(status().isOk()).andExpect(jsonPath("$[0].name").value("Thavasi"));
	}
}
