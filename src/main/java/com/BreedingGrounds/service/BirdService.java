package com.BreedingGrounds.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.UUIDEditor;
import org.springframework.stereotype.Service;

import com.BreedingGrounds.dao.BirdDao;
import com.BreedingGrounds.model.birds.BirdAllInfo;
import com.BreedingGrounds.model.birds.BirdInput;

@Service
public class BirdService extends GenericService{
	private final BirdDao birdDao;
	private final JWTTokenService jwtTokenService;
	
	@Autowired
	public BirdService(@Qualifier("bird") BirdDao birdDao, JWTTokenService jwtTokenService) {
		this.birdDao = birdDao;
		this.jwtTokenService = jwtTokenService;
	}
	
	public int addBird(BirdInput birdInput, HttpServletRequest request) {
		Optional<Object> userProfileId = jwtTokenService.getPropertyInJWTtokenOnRequest(request, "userProfileId");
		UUIDEditor uuidEditor = new UUIDEditor();
		uuidEditor.setAsText(userProfileId.get().toString());
		
		return this.getBirdDao().insertBird(birdInput, (UUID) uuidEditor.getValue());
	}
	
	public List<BirdAllInfo> getAllBirds(HttpServletRequest request){
		Optional<Object> userProfileId = jwtTokenService.getPropertyInJWTtokenOnRequest(request, "userProfileId");
		UUIDEditor uuidEditor = new UUIDEditor();
		uuidEditor.setAsText(userProfileId.get().toString());
		
		return this.getBirdDao().selectAllBirds((UUID) uuidEditor.getValue());
	}
	
	public Optional<BirdAllInfo> getBirdById(UUID id, HttpServletRequest request){
		Optional<Object> userProfileId = jwtTokenService.getPropertyInJWTtokenOnRequest(request, "userProfileId");
		UUIDEditor uuidEditor = new UUIDEditor();
		uuidEditor.setAsText(userProfileId.get().toString());
		
		return this.getBirdDao().selectBirdById(id, (UUID) uuidEditor.getValue());
	}
	
	public Optional<BirdAllInfo> getBirdById(UUID id, UUID userProfileId){
		return this.getBirdDao().selectBirdById(id, userProfileId);
	}
	
	public int deleteBirdById(UUID id, HttpServletRequest request){
		Optional<Object> userProfileId = jwtTokenService.getPropertyInJWTtokenOnRequest(request, "userProfileId");
		UUIDEditor uuidEditor = new UUIDEditor();
		uuidEditor.setAsText(userProfileId.get().toString());
				
		return this.getBirdDao().deleteBirdById(id, (UUID) uuidEditor.getValue());
	}
	
	public int updateBirdById(UUID id, BirdInput birdInput, HttpServletRequest request) {
		Optional<Object> userProfileId = jwtTokenService.getPropertyInJWTtokenOnRequest(request, "userProfileId");
		UUIDEditor uuidEditor = new UUIDEditor();
		uuidEditor.setAsText(userProfileId.get().toString());
		
		return this.getBirdDao().updateBirdById(id, birdInput, (UUID) uuidEditor.getValue());
	}

	public BirdDao getBirdDao() {
		return birdDao;
	}
}
