package com.management.students.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.students.dto.StaffProfileDTO;
import com.management.students.dto.StaffUpdateDTO;
import com.management.students.entity.Department;
import com.management.students.entity.Staff;
import com.management.students.entity.User;
import com.management.students.exception.ResourceNotFoundException;
import com.management.students.repository.DepartmentRepository;
import com.management.students.repository.StaffRepository;
import com.management.students.repository.UserRepository;

@Service
public class StaffService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private StaffRepository staffRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	
	public Staff createStaff(StaffProfileDTO staffDTO,CustomUserDetails userDetails) {
		Staff staff=new Staff();
		Optional<User> userOpt=userRepository.findByUsername(userDetails.getUsername());
		User user=userOpt.get();
		Optional<Department> optDept=departmentRepository.findByName(staffDTO.getDepartment());
		if(optDept.isEmpty())
			throw new ResourceNotFoundException("");	
		staff.setDepartment(optDept.get());
		staff.setDob(staffDTO.getDob());
		staff.setEmail(user.getEmail());
		staff.setJoiningDate(staffDTO.getJoiningDate());
		staff.setName(user.getUsername());
		staff.setPhone(staffDTO.getPhone());
		staff.setUser(user);
		
        Staff savedStaff = staffRepository.save(staff);
        return savedStaff;
	}

	   
    public Staff getStaffById(Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with id "+id));
        return staff;
    }


	    public List<Staff> getAllStaff() {
	        List<Staff> staffList = staffRepository.findAll();
	        return staffList;
	    }


	    public Staff updateStaff(Long id, StaffUpdateDTO staff) {
	        Staff existingStaff = staffRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with id "+id ));

	        Department department=departmentRepository.findByName(staff.getDepartment()).orElseThrow(()->new ResourceNotFoundException("Department not found with name "));
	        existingStaff.setName(staff.getName());
	        existingStaff.setEmail(staff.getEmail());
	        existingStaff.setPhone(staff.getPhone());
	        existingStaff.setDepartment(department);
	        
	        Staff updatedStaff = staffRepository.save(existingStaff);
	        return updatedStaff;
	    }


	    public void deleteStaff(Long id) {
	        Staff staff = staffRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with id "+id));
	        staffRepository.delete(staff);
	    }

}
