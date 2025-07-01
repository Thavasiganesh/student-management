package com.management.students.exception;

public class AccessDeniedException extends RuntimeException{

	public AccessDeniedException(String ex) {
		super(ex);
	}
}
