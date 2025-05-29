package com.management.students.exception;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger logger=LoggerFactory.getLogger(GlobalExceptionHandler.class);
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> handleValidationErrors(MethodArgumentNotValidException ex){
		logger.error("Unhandled exception occured:{}",ex.getMessage(),ex);
		
		Map<String,String> errors = new HashMap<>();
		List<FieldError> errs=new ArrayList<FieldError>();
		errs.addAll(ex.getBindingResult().getFieldErrors());
		for(FieldError err:errs) {
			errors.put(err.getField(), err.getDefaultMessage());
		}
		
		return ResponseEntity.badRequest().body(errors);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
}
