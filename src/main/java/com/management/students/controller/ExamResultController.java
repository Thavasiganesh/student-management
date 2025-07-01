package com.management.students.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.management.students.entity.ExamResult;
import com.management.students.service.ExamResultService;

@RestController
@RequestMapping("/exam-results")
public class ExamResultController {
	
	 	@Autowired
	    private ExamResultService examResultService;

	 	@PreAuthorize("hasRole('STAFF')")
	    @PostMapping
	    public ResponseEntity<ExamResult> addResult(@RequestBody ExamResult dto) {
	        ExamResult result = examResultService.addResult(dto);
	        return new ResponseEntity<>(result, HttpStatus.CREATED);
	    }

	 	@PreAuthorize("hasRole('STAFF')")
	    @GetMapping
	    public List<ExamResult> getAll() {
	        return examResultService.getAllResults();
	    }
	    
	    @PreAuthorize("hasRole('STUDENT')")
	    @GetMapping("/student/{studentId}")
	    public List<ExamResult> getByStudent(@PathVariable Long studentId) {
	        return examResultService.getResultsByStudent(studentId);
	    }

	    @PreAuthorize("hasAnyRole('STAFF','STUDENT')")
	    @GetMapping("/exam/{examId}")
	    public List<ExamResult> getByExam(@PathVariable Long examId) {
	        return examResultService.getResultsByExam(examId);
	    }
	    
	    @PreAuthorize("hasRole('STAFF')")
	    @DeleteMapping("/exam/{examId}")
	    public ResponseEntity<String> deleteExams(@PathVariable Long examId){
	    	return examResultService.deleteExamsByExamId(examId);
	    }

}
