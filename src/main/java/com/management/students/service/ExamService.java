package com.management.students.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.students.entity.Course;
import com.management.students.entity.Exam;
import com.management.students.exception.ResourceNotFoundException;
import com.management.students.repository.CourseRepository;
import com.management.students.repository.ExamRepository;

@Service
public class ExamService {
	
	 @Autowired
	    private ExamRepository examRepository;

	    @Autowired
	    private CourseRepository courseRepository;


	    public Exam scheduleExam(Exam dto) {
	        Course course = courseRepository.findById(dto.getCourse().getId())
	            .orElseThrow(() -> new ResourceNotFoundException("Course not found"));


	        Exam exam = new Exam();
	        exam.setExamTitle(dto.getExamTitle());
	        exam.setCourse(course);
	        exam.setExamDate(dto.getExamDate());
	        exam.setClassroom(dto.getClassroom());
	        exam.setDuration(dto.getDuration());

	        return examRepository.save(exam);
	    }

	    public List<Exam> getAllExams() {
	        return examRepository.findAll();
	    }

	    public List<Exam> getExamsByCourse(Long courseId) {
	        return examRepository.findByCourseId(courseId);
	    }
}
	


