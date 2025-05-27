package com.management.students.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
@Entity
@Table(name="students")
public class Student {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long Id;
	@NotBlank(message="Name cannnot be empty")
	private String name;
	@NotBlank(message="Provide a department")
	private String department;
	@Email(message="Provide a valid email")
	private String email;
	@Min(value=3,message="Age must be greater than 3")
	private int age;
	@JsonIgnore
	private Boolean isDeleted=false;
	
	public Student() {
		
	}
	public Student(String name,String department,String email,int age) {
		this.name=name;
		this.department=department;
		this.email=email;
		this.age=age;
	}
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
}
