package com.management.students.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.management.students.entity.Exam;
import com.management.students.entity.ExamResult;
import com.management.students.entity.Student;
import com.management.students.exception.ResourceNotFoundException;
import com.management.students.repository.ExamRepository;
import com.management.students.repository.ExamResultRepository;
import com.management.students.repository.StudentRepository;

@Service
public class ExamResultService {
	
	@Autowired
    private ExamResultRepository examResultRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ExamRepository examRepository;

    public ExamResult addResult(ExamResult dto) {
        Student student = studentRepository.findById(dto.getStudent().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        
        Exam exam = examRepository.findById(dto.getExam().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Exam not found"));
        
        ExamResult result = new ExamResult();
        result.setStudent(student);
        result.setExam(exam);
        result.setScore(dto.getScore());
        result.setGrade(dto.getGrade());

        return examResultRepository.save(result);
    }

    public List<ExamResult> getAllResults() {
        return examResultRepository.findAll();
    }

    public List<ExamResult> getResultsByStudent(Long studentId) {
        return examResultRepository.findByStudentId(studentId);
    }

    public List<ExamResult> getResultsByExam(Long examId) {
        return examResultRepository.findByExamId(examId);
    }

	public ResponseEntity<String> deleteExamsByExamId(Long examId) {
		// TODO Auto-generated method stub
		examResultRepository.deleteByExamId(examId);
		return ResponseEntity.ok("Exams deleted successfully");
	}

}
