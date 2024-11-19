package com.FirstEmployeesProject.Exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
	
	private String status;
	private String message;
	private Object details;
	
	public ErrorResponse() {
		// TODO Auto-generated constructor stub
	}

	public ErrorResponse(String status, String message, Object details) {
		super();
		this.status = status;
		this.message = message;
		this.details = details;
	}

}
