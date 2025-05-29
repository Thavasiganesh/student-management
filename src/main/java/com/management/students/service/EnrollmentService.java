package com.management.students.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.students.entity.Course;
import com.management.students.entity.Enrollment;
import com.management.students.entity.Student;
import com.management.students.exception.ResourceNotFoundException;
import com.management.students.repository.CourseRepository;
import com.management.students.repository.EnrollmentRepository;
import com.management.students.repository.StudentRepository;

@Service
public class EnrollmentService {
	
	@Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public Enrollment enrollStudent(Enrollment enrollment) {
        Student student = studentRepository.findById(enrollment.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Student not found with id "));

        
        
        Course course = courseRepository.findById(enrollment.getCourse().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        Enrollment enroll = new Enrollment();
        enroll.setStudent(student);
        enroll.setCourse(course);
        enroll.setEnrollmentDate(enrollment.getEnrollmentDate());

        return enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public List<Enrollment> getEnrollmentsByStudentId(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    public List<Enrollment> getEnrollmentsByCourseId(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }
}

