package com.BreedingGrounds.service;

import java.util.Base64;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BreedingGrounds.model.user.Session;
import com.BreedingGrounds.model.user.User;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTTokenService extends GenericService {
	public static final String SECRET = "breedingGrounds";
	public static final String AUTHORIZATION = "Authorization";
	public static final String BEARER = "Bearer ";
	
	private final SessionService sessionService;
	
	@Autowired
	public JWTTokenService(SessionService sessionService) {
		this.sessionService = sessionService;
	}
	
	public String generateJWTToken(Map<String, Object> payload) {
		String secretEncoded = Base64.getEncoder().encodeToString(SECRET.getBytes());
		String token = Jwts.builder()
				.setClaims(payload)
				.signWith(SignatureAlgorithm.HS512,secretEncoded)
				.compact();
		
		return token;
	}
	
	public Optional<String> getTokenInHeader(HttpServletRequest request) {
		Optional<String> token = Optional.ofNullable(null);
		
		if(request.getHeader(AUTHORIZATION) != null) {
			String authorization = request.getHeader(AUTHORIZATION);
			String jwtToken = authorization.replace(BEARER, "");
			
			token = Optional.of(jwtToken);
		}
		
		return token;
	}
	
	public Optional<String> getSubjectInToken(String jwtToken) {
		Optional<String> subject = Optional.ofNullable(null);
		
		String secretEncoded = Base64.getEncoder().encodeToString(
				SECRET.getBytes());
		
		subject = Optional.of(Jwts.parser().setSigningKey(secretEncoded)
				.parseClaimsJws(jwtToken).getBody().getSubject());
		
		return subject;
	}
	
	public Optional<Object> getClaimInToken(String jwtToken, String claimPropery) {
		Optional<Object> claim = Optional.ofNullable(null);
		
		String secretEncoded = Base64.getEncoder().encodeToString(
				SECRET.getBytes());
		
		claim = Optional.of(Jwts.parser().setSigningKey(secretEncoded)
				.parseClaimsJws(jwtToken).getBody().get(claimPropery, Object.class));
		
		return claim;
	}
	
	public Boolean validateToken(String jwtToken, User user) {
		boolean valid = false;
		Optional<Session> session = Optional.empty();
		
		try {
			if(user.getSessionId() != null) {
				session = sessionService.selectSessionById(user.getSessionId());
			}else {
				return false;
			}
			
			valid = jwtToken.equalsIgnoreCase(session.get().getJwtToken()) && session.get().isValid();
//			valid = session.get().isValid();
				
		} catch (Exception e) {
			throw new JwtException(e.getMessage());
		}
		
		return valid;
	}
	
	public Optional<Object> getPropertyInJWTtokenOnRequest(HttpServletRequest request, String propertyName){
		Optional<Object> property = Optional.empty();
		Optional<String> jwtToken = this.getTokenInHeader(request);
		
		if(jwtToken.isEmpty()) {
			throw new JwtException("Jwt token not found in header.");
		}
		
		property = this.getClaimInToken(jwtToken.get(), propertyName);
		
		return property;
	}
	
}
