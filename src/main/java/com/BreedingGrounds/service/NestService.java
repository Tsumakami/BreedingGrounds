package com.BreedingGrounds.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.UUIDEditor;
import org.springframework.stereotype.Service;

import com.BreedingGrounds.dao.NestDao;
import com.BreedingGrounds.model.nest.Nest;
import com.BreedingGrounds.model.nest.NestInput;

@Service
public class NestService {
	private final NestDao nestDao;
	private final JWTTokenService jwtTokenService;
	
	@Autowired
	public NestService(NestDao nestDao, JWTTokenService jwtTokenService) {
		this.nestDao = nestDao;
		this.jwtTokenService = jwtTokenService;
	}
	
	public int createNest(NestInput nestInput, HttpServletRequest request) {
		Optional<Object> userProfileId = jwtTokenService.getPropertyInJWTtokenOnRequest(request, "userProfileId");
		UUIDEditor uuidEditor = new UUIDEditor();
		uuidEditor.setAsText(userProfileId.get().toString());
		
		return this.nestDao.insertNest(nestInput, (UUID) uuidEditor.getValue());
	}
	
	public List<Nest> getAllNests(HttpServletRequest request){
		Optional<Object> userProfileId = jwtTokenService.getPropertyInJWTtokenOnRequest(request, "userProfileId");
		UUIDEditor uuidEditor = new UUIDEditor();
		uuidEditor.setAsText(userProfileId.get().toString());
		
		return this.nestDao.selectAllNests((UUID) uuidEditor.getValue());
	}
	
	public Optional<Nest> getNestById(UUID id, HttpServletRequest request){
		Optional<Object> userProfileId = jwtTokenService.getPropertyInJWTtokenOnRequest(request, "userProfileId");
		UUIDEditor uuidEditor = new UUIDEditor();
		uuidEditor.setAsText(userProfileId.get().toString());
		
		return this.nestDao.selectNestById(id, (UUID) uuidEditor.getValue());
	}
	
	public int deleteNestById(UUID id, HttpServletRequest request){
		Optional<Object> userProfileId = jwtTokenService.getPropertyInJWTtokenOnRequest(request, "userProfileId");
		UUIDEditor uuidEditor = new UUIDEditor();
		uuidEditor.setAsText(userProfileId.get().toString());
				
		return this.nestDao.deleteNestById(id, (UUID) uuidEditor.getValue());
	}
	
	public int updateNestById(UUID id, NestInput nestInput, HttpServletRequest request) {
		Optional<Object> userProfileId = jwtTokenService.getPropertyInJWTtokenOnRequest(request, "userProfileId");
		UUIDEditor uuidEditor = new UUIDEditor();
		uuidEditor.setAsText(userProfileId.get().toString());
		
		return this.nestDao.updateNestById(id, nestInput, (UUID) uuidEditor.getValue());
	}
	
}
