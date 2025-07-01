package com.management.students.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.management.students.dto.StaffProfileDTO;
import com.management.students.dto.StaffUpdateDTO;
import com.management.students.entity.Staff;
import com.management.students.service.CustomUserDetails;
import com.management.students.service.CustomUserDetailsService;
import com.management.students.service.StaffService;

@RestController
@RequestMapping("/staffs")
public class StaffController {

	@Autowired
	private StaffService staffService;
	
	
	@PreAuthorize("hasAnyRole('STAFF','ADMIN')")
	@PostMapping
    public ResponseEntity<Staff> createStaff(@RequestBody StaffProfileDTO staff,@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(staffService.createStaff(staff,userDetails));
    }

	@PreAuthorize("hasAnyRole('STAFF','ADMIN','STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<Staff> getStaffById(@PathVariable Long id) {
        return ResponseEntity.ok(staffService.getStaffById(id));
    }

	@PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    @GetMapping
    public ResponseEntity<List<Staff>> getAllStaff() {
        return ResponseEntity.ok(staffService.getAllStaff());
    }

	@PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @PutMapping("/{id}")
    public ResponseEntity<Staff> updateStaff(@PathVariable Long id, @RequestBody StaffUpdateDTO staff) {
        return ResponseEntity.ok(staffService.updateStaff(id, staff));
    }

	@PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return ResponseEntity.noContent().build();
    }
}
