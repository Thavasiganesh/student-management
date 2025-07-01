package com.management.students.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.management.students.entity.ExamResult;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {

	List<ExamResult> findByStudentId(Long studentId);
    List<ExamResult> findByExamId(Long examId);
    void deleteByExamId(Long examId);
}
