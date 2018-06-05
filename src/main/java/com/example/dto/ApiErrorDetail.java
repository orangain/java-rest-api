package com.example.dto;

import java.util.List;

public class ApiErrorDetail {
	private String message;
	private List<String> path;

	public ApiErrorDetail() {

	}

	public ApiErrorDetail(String message, List<String> path) {
		super();
		this.message = message;
		this.path = path;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getPath() {
		return path;
	}

	public void setPath(List<String> path) {
		this.path = path;
	}
}
