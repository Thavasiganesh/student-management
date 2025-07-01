package com.management.students.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.management.students.dto.RoleDTO;
import com.management.students.service.RoleService;

@RestController
@RequestMapping("/roles")
public class RoleController {
	private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }
}

