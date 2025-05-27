package com.management.students.dto;

import java.util.List;

import com.management.students.entity.Student;

public class StudentPageResponse {
	private List<Student> students;
	private int curPage;
	private int totPages;
	private long totItems;
	private String sortBy;
	private String direction;
	
	public StudentPageResponse(List<Student> stud) {
		this.students=stud;
	}
	
	public StudentPageResponse(List<Student> students, int curPage, int totPages, long l, String sortBy,
			String direction) {
		this.students = students;
		this.curPage = curPage;
		this.totPages = totPages;
		this.totItems = l;
		this.sortBy = sortBy;
		this.direction = direction;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getTotPages() {
		return totPages;
	}

	public void setTotPages(int totPages) {
		this.totPages = totPages;
	}

	public long getTotItems() {
		return totItems;
	}

	public void setTotItems(int totItems) {
		this.totItems = totItems;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	
}
