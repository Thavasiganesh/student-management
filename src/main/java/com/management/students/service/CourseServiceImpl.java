package com.management.students.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import com.management.students.dto.CourseDTO;
import com.management.students.entity.Course;
import com.management.students.entity.Department;
import com.management.students.exception.ResourceNotFoundException;
import com.management.students.repository.CourseRepository;
import com.management.students.repository.DepartmentRepository;

@Service
public class CourseServiceImpl implements CourseService {
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Override
	public List<CourseDTO> getAllCourses(){
		return courseRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
	}
	
	@Override
	public CourseDTO getCourseById(Long id) {
		Optional<Course> course=courseRepository.findById(id);
		if(course.isPresent())
			return convertToDTO(course.get());
		throw new ResourceNotFoundException("Course not found with id "+id);
	}
	
	@Override
	public CourseDTO createCourse(CourseDTO courseDto,Long departmentId) {
		Department department=departmentRepository.findById(departmentId).orElseThrow(()->new ResourceNotFoundException("Department not found with id "+departmentId));
		Course course =new Course();
		course.setId(courseDto.getId());
		course.setCourseName(courseDto.getName());
		course.setCourseCode(courseDto.getCode());
		course.setCredits(courseDto.getCredits());
		course.setDepartment(department);
		course.setDescription(courseDto.getDescription());
		return convertToDTO(courseRepository.save(course));
	}
	
	@Override
	public CourseDTO updateCourse(Long id,CourseDTO update) {
		Optional<Course> optCourse=courseRepository.findById(id);
		if(optCourse.isPresent()) {
			Course course=optCourse.get();
			course.setCourseName(update.getName());
			course.setCourseCode(update.getCode());
			course.setCredits(update.getCredits());
			course.setDescription(update.getDescription());
			return convertToDTO(courseRepository.save(course));
		}
		throw new ResourceNotFoundException("Course not found with id "+id);
		
	}

	@Override
	public void deleteCourse(Long id) {
		courseRepository.deleteById(id);
	}
	
	private CourseDTO convertToDTO(Course course) {
		CourseDTO courseDto=new CourseDTO();
		courseDto.setId(course.getId());
		courseDto.setName(course.getCourseName());
		courseDto.setCode(course.getCourseCode());
		courseDto.setCredits(course.getCredits());
		courseDto.setDescription(course.getDescription());
		return courseDto;
	}

	@Override
	public List<CourseDTO> getCoursesByDepartment(String departmentName) {
		// TODO Auto-generated method stub
		Optional<Department> departmentId=departmentRepository.findByName(departmentName);
		if(departmentId.isEmpty())
			throw new ResourceNotFoundException("Department not found.");
		List<Course> courses=courseRepository.findByDepartmentId(departmentId.get().getId());
		List<CourseDTO> coursesDTO=new ArrayList<CourseDTO>();
		for(Course course:courses) {
			coursesDTO.add( convertToDTO(course));
		}
		return coursesDTO;
	}
}
