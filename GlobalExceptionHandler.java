package com.FirstEmployeesProject.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


import jakarta.persistence.EntityNotFoundException;
@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception e, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse("error", "internal server error", e.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e,
			WebRequest request) {
//		List<FieldError> fieldErrors= e.getBindingResult().getFieldErrors();
//		List<String> errorMessages=fieldErrors.stream().map(fieldError->fieldError.getField()+ ": " + fieldError.getDefaultMessage())
//                .collect(Collectors.toList());
		ErrorResponse errorResponse = new ErrorResponse("error", "validation Failed",
				e.getBindingResult().getFieldError().getDefaultMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleEntityNotFoundException(Exception e, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse("Error", "Entity Not Found", e.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleAllException(ResourceNotFoundException e, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.toString(), e.getMessage(),
				"Something went wrong");
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e, WebRequest request) {
	    ErrorResponse errorResponse = new ErrorResponse("error", e.getMessage(), null);
	    return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
	}


}
