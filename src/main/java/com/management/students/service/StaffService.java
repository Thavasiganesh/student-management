package com.management.students.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.students.entity.Staff;
import com.management.students.exception.ResourceNotFoundException;
import com.management.students.repository.StaffRepository;

@Service
public class StaffService {
	
	@Autowired 
	private StaffRepository staffRepository;
	
	 public Staff createStaff(Staff staff) {
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


	    public Staff updateStaff(Long id, Staff staff) {
	        Staff existingStaff = staffRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with id "+id ));

	        existingStaff.setName(staff.getName());
	        existingStaff.setEmail(staff.getEmail());
	        existingStaff.setPhone(staff.getPhone());
	        existingStaff.setRole(staff.getRole());

	        Staff updatedStaff = staffRepository.save(existingStaff);
	        return updatedStaff;
	    }


	    public void deleteStaff(Long id) {
	        Staff staff = staffRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with id "+id));
	        staffRepository.delete(staff);
	    }

}
