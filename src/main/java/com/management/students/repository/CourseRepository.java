package com.management.students.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.management.students.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

	Optional<Course> findByCourseName(String name);
	
}
