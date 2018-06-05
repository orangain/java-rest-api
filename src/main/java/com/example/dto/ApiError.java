package com.example.dto;

import java.util.List;

public class ApiError {
	private String message;
	private List<ApiErrorDetail> details;

	public ApiError() {
	}

	public ApiError(String message) {
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<ApiErrorDetail> getDetails() {
		return details;
	}

	public void setDetails(List<ApiErrorDetail> details) {
		this.details = details;
	}
}
