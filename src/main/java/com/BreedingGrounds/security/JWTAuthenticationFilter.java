package com.BreedingGrounds.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.BreedingGrounds.model.user.User;
import com.BreedingGrounds.service.JWTTokenService;
import com.BreedingGrounds.service.UserProfileService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

	public static final String ACCESS_TOKEN = "Access-token";
	public static final String SUCCESS = "success";
	public static final String MESSAGE = "message";
	public static final String APPLICATION_JSON = "application/json";
	
	private JWTTokenService jwtTokenService;
	private UserProfileService userProfileService;
	
	public JWTAuthenticationFilter( 
			JWTTokenService jwtTokenService,
			UserProfileService userProfileService
			) {
		this.jwtTokenService = jwtTokenService;
		this.userProfileService = userProfileService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		Optional<String> jwtToken = jwtTokenService.getTokenInHeader(request);
		
		if (!jwtToken.isEmpty()) {
			try {
				Optional<Object> username = jwtTokenService.getClaimInToken(jwtToken.get(), "username");
				Optional<User> user = userProfileService.getUserProfileByEmail((String) username.get());
				
				boolean tokenIsValid = false;
				tokenIsValid = jwtTokenService.validateToken(jwtToken.get(), user.get());
				
				if(tokenIsValid && SecurityContextHolder.getContext().getAuthentication() == null) {
					UserDetails userDetails = (UserDetails) user.get();
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, 
							null, 
							Arrays.asList(
									new SimpleGrantedAuthority("USER_FREE"), 
									new SimpleGrantedAuthority("USER_PREMIUM")));
					
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					logger.info("authenticated user " + username + ", setting security context");
					
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
				
			} catch (IllegalArgumentException e) {
				logger.error("an error occured during getting username from token", e);
			} catch (ExpiredJwtException e) {
				logger.warn("the token is expired and not valid anymore", e);
			} catch (SignatureException e) {
				logger.error("Authentication Failed. Username or Password not valid.");
			}
		} else if(!request.getServletPath().contains("login") && !request.getServletPath().contains("logout")) {
			logger.warn("couldn't find bearer string, will ignore the header");
		}
		
		filterChain.doFilter(request, response);
		
	}
	
}
