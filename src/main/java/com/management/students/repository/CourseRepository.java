package com.management.students.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.management.students.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {


}
