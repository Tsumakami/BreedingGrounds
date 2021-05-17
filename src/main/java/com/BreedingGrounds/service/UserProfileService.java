package com.BreedingGrounds.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.BreedingGrounds.dao.UserProfileDao;
import com.BreedingGrounds.model.ResponseObject;
import com.BreedingGrounds.model.user.Login;
import com.BreedingGrounds.model.user.Session;
import com.BreedingGrounds.model.user.User;
import com.BreedingGrounds.model.user.UserProfile;
import com.BreedingGrounds.validator.UserValidator;

import io.jsonwebtoken.JwtException;

@Service
public class UserProfileService extends GenericService implements UserDetailsService{
	
	private static final String EMAIL_ERROR = "E-mail inválido.";
	private static final String CPF_ERROR = "CPF inválido.";
	private static final String USER_NOT_FOUND = "Usuário não encontrado.";
	private static final String USER_EXISTS = "Usuário já possui cadastro.";
	public static final String ACCESS_TOKEN = "Access-token";
	public static final String SUCCESS = "success";
	public static final String MESSAGE = "message";
	
	private final UserProfileDao userProfileDao;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final SessionService sessionService;
	private final JWTTokenService jwtTokenService;
	
	@Autowired
	public UserProfileService(@Qualifier("userProfile") UserProfileDao userProfileDao, 
			BCryptPasswordEncoder bCryptPasswordEncoder,
			SessionService sessionService,
			JWTTokenService jwtTokenService) {
		
		this.userProfileDao = userProfileDao;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.sessionService = sessionService;
		this.jwtTokenService = jwtTokenService;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user = null;
		Optional<User> oUserProfile = this.getUserProfileByEmail(username);
		
		if(oUserProfile == null || oUserProfile.isEmpty()) {
			throw new UsernameNotFoundException(USER_NOT_FOUND);
		}else {
			user= (UserDetails) oUserProfile.get();
		}
		
		return user;
	}
	
	public int addUserProfile(UserProfile userProfile) {
		
		boolean isValidEmail = UserValidator.testEmail(userProfile.getEmail());
		boolean isValidCPF = UserValidator.testCpf(userProfile.getCpf(), true);
		boolean userExists = this.getUserProfileByEmail(userProfile.getEmail()).isPresent();
		
		if(userExists) {
			throw new IllegalStateException(USER_EXISTS);
		}
		
		if(!isValidEmail) {
			throw new IllegalStateException(EMAIL_ERROR);
		}
		
		if(!isValidCPF) {
			throw new IllegalStateException(CPF_ERROR);
		}
		
		String password = bCryptPasswordEncoder.encode(userProfile.getPassword());
		
		logger.debug("Encrypting password, password={}", password);
		
		userProfile.setPassword(password);
		
		return this.getUserProfileDao().addUserProfile(userProfile);
	}
	
	public List<User> getAllUserProfile(){
		return this.getUserProfileDao().selectAllUserProfile();
	}
	
	public Optional<User> getUserProfileById(UUID id){
		return this.getUserProfileDao().selectUserProfileById(id);
	}
	
	public Optional<User> getUserProfileByEmail(String email){
		return this.getUserProfileDao().getUserProfileByEmail(email);
	}
	
	public int deleteUserProfileById(UUID id){
		return this.getUserProfileDao().deleteUserProfileById(id);
	}
	
	public int updateUserProfileById(UUID id, UserProfile userProfile) {
		return this.getUserProfileDao().updateUserProfileById(id, userProfile);
	}
	
	public int updatePasswordById(UUID id, UserProfile userProfile) {
		String password = bCryptPasswordEncoder.encode(userProfile.getPassword());
		
		logger.debug("Encrypting password, password={}", password);
		
		userProfile.setPassword(password);
		
		return this.getUserProfileDao().updatePasswordById(id, userProfile);
	}
	
	public int updateEnableProfileByEmail(String email, boolean enabled) {
		return this.getUserProfileDao().enableUserProfile(email, enabled);
	}
	
	public int updateSessionById(UUID id, UUID sessionId) {
		return this.getUserProfileDao().updateSessionById(id, sessionId);
	}
	
	public UserProfileDao getUserProfileDao() {
		return userProfileDao;
	}

	public ResponseObject loginUserProfile(Login login) {
		
		logger.info(login.toString());
		
		Optional<User> user = this.authenticate(login.getUsername(), login.getPassword());

		if(user.isEmpty()) {
			return failedLogin();
		}
		
		Map<String, Object> claims = new HashMap<String, Object>();
		Date dateNow = new Date();
		claims.put("createnDate", dateNow);
		claims.put("username", login.getUsername());
		
		String jwttoken = jwtTokenService.generateJWTToken(claims);
		
		Optional<Session> session = Optional.empty();
		
		int updated = 0;
		if(user.get().getSessionId() == null) {
			session = Optional.of(new Session(jwttoken, dateNow, true));
			
			sessionService.addSession(session.get());
			updated = this.updateSessionById(user.get().getId(), session.get().getId());
		}else {
			session = sessionService.selectSessionById(user.get().getSessionId());
			
			session.get().setJwtToken(jwttoken);
			session.get().setCreateDate(dateNow);
			session.get().setValid(true);
			
			updated = sessionService.updateSessionById(session.get().getId(), session.get());
		}
		
		if(updated == 1){
			
			logger.info("Success in {}", login.toString());
			
			return successLogin(session.get());
		}else {
			logger.info("Failed in {}", login.toString());
			
			return failedLogin();
		}
		
	}
	
	private ResponseObject failedLogin() {
		ResponseObject resp = new ResponseObject();
		resp.setSuccess(false);
		resp.setMessage("Usuário ou senha inválidos.");
		
		return resp;
	}
	
	private ResponseObject successLogin(Session session) {
		ResponseObject resp = new ResponseObject();
		resp.setSuccess(true);
		resp.setAccessToken(session.getJwtToken());
		
		return resp;
	}

	public Optional<User> authenticate(String username, String password) {
		Optional<User> userValid = Optional.empty();
		
		Optional<User> user = this.getUserProfileByEmail(username);
		
		if(user.isPresent()) {
			if(bCryptPasswordEncoder.matches(password, user.get().getPassword())) {
				userValid = user;
			}
		}
				
		return userValid;
	}

	public boolean logout(UUID id) {
		
		logger.debug("Logout for id={}", id);
		
		if(id == null) {
			throw new RuntimeException("User id is null.");
		}
		
		Optional<User> user = this.getUserProfileById(id);
		
		if(user.isEmpty()) {
			throw new RuntimeException("User not found for Logout.");
		}
		
		Optional<Session> session = sessionService.selectSessionById(user.get().getSessionId());
		
		if(session.isEmpty()) {
			throw new RuntimeException("Session has already ended.");
		}
		
		session.get().setJwtToken(null);
		session.get().setValid(false);
		session.get().setCreateDate(null);
		
		sessionService.updateSessionById(session.get().getId(), session.get());
		
		return true;
	}

	public User getMyUserProfile(HttpServletRequest request) {
		Optional<String> jwtToken = jwtTokenService.getTokenInHeader(request);
		
		if(jwtToken.isEmpty()) {
			throw new JwtException("Jwt token not found in header.");
		}
		
		Optional<Object> username = jwtTokenService.getClaimInToken(jwtToken.get(), "username");
		
		if(username.isEmpty()) {
			throw new JwtException("Jwt token is invalid.");
		}
		
		Optional<User> user = this.getUserProfileByEmail(username.get().toString());
		
		if(user.isEmpty()) {
			throw new JwtException("Jwt token is invalid for username " + username.get().toString());
		}
		
		return user.get();
	}

	
	
}
