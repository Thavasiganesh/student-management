package com.management.students.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.management.students.dto.CourseDTO;
import com.management.students.service.CourseService;

@RestController
@RequestMapping("/courses")
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	
	@GetMapping
    public List<CourseDTO> getAllCourses() {
        return courseService.getAllCourses();
    }
	
	@GetMapping(params="department")
	public List<CourseDTO> getCoursesByDepartment(@RequestParam String department){
		return courseService.getCoursesByDepartment(department);
	}
	
    @GetMapping("/{id}")
    public CourseDTO getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }

    @PostMapping("/department/{deptId}")
    public CourseDTO createCourse(@RequestBody CourseDTO course, @PathVariable Long deptId) {
        return courseService.createCourse(course, deptId);
    }

    @PutMapping("/{id}")
    public CourseDTO updateCourse(@PathVariable Long id, @RequestBody CourseDTO course) {
        return courseService.updateCourse(id, course);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
    }
}
