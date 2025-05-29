package com.management.students.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.management.students.entity.Exam;

public interface ExamRepository extends JpaRepository<Exam, Long> {
	List<Exam> findByCourseId(Long courseId);
}
