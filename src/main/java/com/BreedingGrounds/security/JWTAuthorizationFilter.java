package com.BreedingGrounds.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.BreedingGrounds.service.JWTTokenService;
import com.BreedingGrounds.service.UserProfileService;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	
	private final UserProfileService userProfileService;
	private final JWTTokenService jwtTokenService;
		
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, 
			UserProfileService userProfileService,
			JWTTokenService jwtTokenService) {
		super(authenticationManager);
		this.userProfileService = userProfileService;
		this.jwtTokenService = jwtTokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(request);
		
		if(authenticationToken != null) {
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		
		chain.doFilter(request, response);
		
	}
	
	
	
	private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
		
		try {
			Optional<String> jwtToken = jwtTokenService.getTokenInHeader(request);
			
			if(jwtToken.isEmpty()) {
				throw new RuntimeException("Invalid authorization.");
			}
			logger.debug("getAuthenticationToken - Get token=" + jwtToken.get());
			
			String username =(String) jwtTokenService.getClaimInToken(jwtToken.get(), "username").get();
			
			UserDetails userDetails = userProfileService.loadUserByUsername(username);
					
			if(userDetails == null) return null;
			
			return new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
			
		} catch (RuntimeException e) {
			logger.error("getAuthenticationToken - Error=", e);
		}
		
		return null;
	}
}
