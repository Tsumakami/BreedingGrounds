package com.BreedingGrounds.security;


import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final String SUCCESS = "success";
	private static final String MESSAGE = "message";
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		JSONObject json = new JSONObject();
		json.put(SUCCESS, false);
		json.put(MESSAGE, authException.getMessage());	

		try {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
			response.getWriter().write(json.toString(4));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

}
