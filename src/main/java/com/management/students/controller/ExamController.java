package com.management.students.controller;

import java.util.List;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.management.students.entity.Exam;
import com.management.students.service.ExamService;

@RestController
@RequestMapping("/exams")
public class ExamController {
	@Autowired
    private ExamService examService;

    @PostMapping
    public ResponseEntity<Exam> scheduleExam(@RequestBody Exam dto) {
        Exam exam = examService.scheduleExam(dto);
        return new ResponseEntity<>(exam, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Exam> getAll() {
        return examService.getAllExams();
    }

    @GetMapping("/course/{courseId}")
    public List<Exam> getByCourse(@PathVariable Long courseId) {
        return examService.getExamsByCourse(courseId);
    }
}
