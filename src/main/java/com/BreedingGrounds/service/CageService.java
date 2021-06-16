package com.BreedingGrounds.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.UUIDEditor;
import org.springframework.stereotype.Service;

import com.BreedingGrounds.dao.CageDao;
import com.BreedingGrounds.model.cage.Cage;
import com.BreedingGrounds.model.cage.CageInput;

@Service
public class CageService {
	private final CageDao cageDao;
	private final JWTTokenService jwtTokenService;
	
	@Autowired
	public CageService(CageDao cageDao, JWTTokenService jwtTokenService) {
		this.cageDao = cageDao;
		this.jwtTokenService = jwtTokenService;
	}
	
	public int createCage(CageInput cageInput, HttpServletRequest request) {
		Optional<Object> userProfileId = jwtTokenService.getPropertyInJWTtokenOnRequest(request, "userProfileId");
		UUIDEditor uuidEditor = new UUIDEditor();
		uuidEditor.setAsText(userProfileId.get().toString());
		
		return this.cageDao.insertCage(cageInput, (UUID) uuidEditor.getValue());
	}
	
	public List<Cage> getAllCages(HttpServletRequest request){
		Optional<Object> userProfileId = jwtTokenService.getPropertyInJWTtokenOnRequest(request, "userProfileId");
		UUIDEditor uuidEditor = new UUIDEditor();
		uuidEditor.setAsText(userProfileId.get().toString());
		
		return this.cageDao.selectAllCages((UUID) uuidEditor.getValue());
	}
	
	public Optional<Cage> getCageById(UUID id, HttpServletRequest request){
		Optional<Object> userProfileId = jwtTokenService.getPropertyInJWTtokenOnRequest(request, "userProfileId");
		UUIDEditor uuidEditor = new UUIDEditor();
		uuidEditor.setAsText(userProfileId.get().toString());
		
		return this.cageDao.selectCageById(id, (UUID) uuidEditor.getValue());
	}
	
	public int deleteCageById(UUID id, HttpServletRequest request){
		Optional<Object> userProfileId = jwtTokenService.getPropertyInJWTtokenOnRequest(request, "userProfileId");
		UUIDEditor uuidEditor = new UUIDEditor();
		uuidEditor.setAsText(userProfileId.get().toString());
				
		return this.cageDao.deleteCageById(id, (UUID) uuidEditor.getValue());
	}
	
	public int updateCageById(UUID id, CageInput cageInput, HttpServletRequest request) {
		Optional<Object> userProfileId = jwtTokenService.getPropertyInJWTtokenOnRequest(request, "userProfileId");
		UUIDEditor uuidEditor = new UUIDEditor();
		uuidEditor.setAsText(userProfileId.get().toString());
		
		return this.cageDao.updateCageById(id, cageInput, (UUID) uuidEditor.getValue());
	}
	
}
