package com.management.students.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.management.students.entity.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

}
