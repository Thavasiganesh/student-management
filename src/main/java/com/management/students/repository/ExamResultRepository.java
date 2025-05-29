package com.management.students.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.management.students.entity.ExamResult;

public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {

	List<ExamResult> findByStudentId(Long studentId);
    List<ExamResult> findByExamId(Long examId);
}
