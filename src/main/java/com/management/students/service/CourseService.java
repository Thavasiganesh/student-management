package com.management.students.service;

import java.util.List;

import com.management.students.dto.CourseDTO;

public interface CourseService {

	List<CourseDTO> getAllCourses();

	CourseDTO getCourseById(Long id);

	CourseDTO createCourse(CourseDTO courseDto, Long departmentId);

	CourseDTO updateCourse(Long id, CourseDTO update);

	void deleteCourse(Long id);

	List<CourseDTO> getCoursesByDepartment(String departmentName);

}