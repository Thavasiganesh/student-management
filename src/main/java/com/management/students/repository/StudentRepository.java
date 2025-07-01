package com.management.students.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.management.students.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	List<Student> findByNameContainingIgnoreCase(String name);
	List<Student> findByDepartmentNameContainingIgnoreCase(String department);
	Page<Student> findByIsDeletedFalse(Pageable pages);
	Optional<Student> findByIdAndIsDeletedFalse(Long id);
	Optional<Student> findByEmail(String username);
}
