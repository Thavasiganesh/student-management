package com.management.students.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.management.students.entity.Staff;

public interface StaffRepository extends JpaRepository<Staff, Long> {

}
