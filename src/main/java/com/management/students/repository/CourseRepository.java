package com.management.students.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.management.students.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

	Optional<Course> findByCourseName(String name);

	List<Course> findByDepartmentId(Long departmentId);
	
}
