package com.visible.thred.documentAi.exception;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

	@ExceptionHandler(MyResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Map<String, Object>> handleMyResourceNotFoundException(MyResourceNotFoundException ex) {
		Map<String, Object> response = new HashMap<>();
		response.put("message", "Resourse not found.. MyResourceNotFoundException");
		response.put("data", null);
		response.put("exception", ex.getMessage());
		response.put("code", HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(SynonymsNotSupportedException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Map<String, Object>> handleSynonymsNotSupportedException(SynonymsNotSupportedException ex) {
		Map<String, Object> response = new HashMap<>();
		response.put("message", "Invalid input.. SynonymsNotSupportedException");
		response.put("data", null);
		response.put("exception", ex.getMessage());
		response.put("code", HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException e) {
		Map<String, Object> response = new HashMap<>();
		response.put("message", "Invalid date format. Please use 'dd-MM-yyyy HH:mm'");
		response.put("data", null);
		response.put("exception", e.getMessage());
		response.put("code", HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}
