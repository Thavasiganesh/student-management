package com.management.students.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import com.management.students.entity.Course;
import com.management.students.entity.Department;
import com.management.students.exception.ResourceNotFoundException;
import com.management.students.repository.CourseRepository;
import com.management.students.repository.DepartmentRepository;

@Service
public class CourseService {
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	public List<Course> getAllCourses(){
		return courseRepository.findAll();
	}
	
	public Course getCourseById(Long id) {
		return  courseRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Course not found with id "+id));
	}
	
	public Course createCourse(Course course,Long departmentId) {
		Department department=departmentRepository.findById(departmentId).orElseThrow(()->new ResourceNotFoundException("Department not found with id "+departmentId));
		course.setDepartment(department);
		return courseRepository.save(course);
	}
	
	public Course updateCourse(Long id,Course update) {
		Course course=getCourseById(id);
		course.setCourseName(update.getCourseName());
		course.setCredits(update.getCredits());
		return courseRepository.save(course);
	}

	public void deleteCourse(Long id) {
		Course course=getCourseById(id);
		courseRepository.delete(course);
	}
}
