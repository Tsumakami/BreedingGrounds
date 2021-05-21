package com.BreedingGrounds.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.UUIDEditor;
import org.springframework.stereotype.Service;

import com.BreedingGrounds.dao.SpecieDao;
import com.BreedingGrounds.model.Specie;

@Service
public class SpecieService {
	
	private final SpecieDao specieDao;
	private final JWTTokenService jwtTokenService;
	
	@Autowired
	public SpecieService(@Qualifier("specie") SpecieDao specieDao, JWTTokenService jwtTokenService) {
		this.specieDao = specieDao;
		this.jwtTokenService = jwtTokenService;
	}
	
	public int addSpecie(Specie specie, HttpServletRequest request) {
		Optional<Object> userProfileId = jwtTokenService.getPropertyInJWTtokenOnRequest(request, "userProfileId");
		UUIDEditor uuidEditor = new UUIDEditor();
		uuidEditor.setAsText(userProfileId.get().toString());
		
		return this.getSpecieDao().insertSpecie(specie, (UUID) uuidEditor.getValue());
	}
	
	public List<Specie> getAllSpecies(HttpServletRequest request){
		Optional<Object> userProfileId = jwtTokenService.getPropertyInJWTtokenOnRequest(request, "userProfileId");
		UUIDEditor uuidEditor = new UUIDEditor();
		uuidEditor.setAsText(userProfileId.get().toString());
		
		return this.getSpecieDao().selectAllSpecie((UUID) uuidEditor.getValue());
	}
	
	public Optional<Specie> getSpecieById(UUID id, HttpServletRequest request){
		Optional<Object> userProfileId = jwtTokenService.getPropertyInJWTtokenOnRequest(request, "userProfileId");
		UUIDEditor uuidEditor = new UUIDEditor();
		uuidEditor.setAsText(userProfileId.get().toString());
				
		return this.getSpecieDao().selectSpecieById(id, (UUID) uuidEditor.getValue());
	}
	
	public Optional<Specie> getSpecieById(UUID id, UUID userProfileId){
		return this.getSpecieDao().selectSpecieById(id, userProfileId);
	}
	
	public int deleteSpecieById(UUID id, HttpServletRequest request){
		Optional<Object> userProfileId = jwtTokenService.getPropertyInJWTtokenOnRequest(request, "userProfileId");
		UUIDEditor uuidEditor = new UUIDEditor();
		uuidEditor.setAsText(userProfileId.get().toString());
		
		return this.getSpecieDao().deleteSpecieById(id, (UUID) uuidEditor.getValue());
	}
	
	public int updateSpecieById(UUID id, Specie specie, HttpServletRequest request) {
		Optional<Object> userProfileId = jwtTokenService.getPropertyInJWTtokenOnRequest(request, "userProfileId");
		UUIDEditor uuidEditor = new UUIDEditor();
		uuidEditor.setAsText(userProfileId.get().toString());
		
		return this.getSpecieDao().updateSpecieById(id, specie, (UUID) uuidEditor.getValue());
	}
	
	public SpecieDao getSpecieDao() {
		return specieDao;
	}
	
}
