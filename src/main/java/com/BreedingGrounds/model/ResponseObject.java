package com.BreedingGrounds.model;

import java.io.Serializable;

public class ResponseObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public boolean success;
	public String message;
	public String accessToken;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
