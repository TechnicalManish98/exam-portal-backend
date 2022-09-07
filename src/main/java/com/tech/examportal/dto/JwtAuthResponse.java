package com.tech.examportal.dto;


public class JwtAuthResponse {

	private String token;

	public JwtAuthResponse(String jwtToken) {
		
		this.token=jwtToken;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
	
}
