package com.management.students.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.students.entity.Department;
import com.management.students.exception.ResourceNotFoundException;
import com.management.students.repository.DepartmentRepository;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;
	
	public List<Department> getAllDepartments(){
		return departmentRepository.findAll();
	}
	
	public Department getDepartmentById(Long id) {
		return departmentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Department not found with id "+id));
	}
	
	public Department  createDepartment(Department department) {
		return departmentRepository.save(department);
	}
	
	public Department updateDepartment(Long id,Department updated) {
		Optional<Department> opt =departmentRepository.findById(id);
		if(opt.isPresent()) {
			Department existing=opt.get();
			existing.setName(updated.getName());
			return  departmentRepository.save(existing);
		}
		throw new ResourceNotFoundException("Department not found with id "+id);
	}
	
	public void deleteDepartment(Long id) {
		departmentRepository.deleteById(id);
	}
}
