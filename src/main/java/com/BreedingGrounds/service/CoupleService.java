package com.BreedingGrounds.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.UUIDEditor;
import org.springframework.stereotype.Service;

import com.BreedingGrounds.dao.CoupleDao;
import com.BreedingGrounds.model.couple.Couple;
import com.BreedingGrounds.model.couple.CoupleInput;
import com.BreedingGrounds.model.couple.Posture;
import com.BreedingGrounds.model.couple.PostureInput;

@Service
public class CoupleService {
	private final CoupleDao coupleDao;
	private final JWTTokenService jwtTokenService;
	
	@Autowired
	public CoupleService(CoupleDao coupleDao, JWTTokenService jwtTokenService) {
		this.coupleDao = coupleDao;
		this.jwtTokenService = jwtTokenService;
	}
	
	public int createCouple(CoupleInput coupleInput, HttpServletRequest request) {
		Optional<Object> userProfileId = jwtTokenService.getPropertyInJWTtokenOnRequest(request, "userProfileId");
		UUIDEditor uuidEditor = new UUIDEditor();
		uuidEditor.setAsText(userProfileId.get().toString());
		
		return this.coupleDao.insertCouple(coupleInput, (UUID) uuidEditor.getValue());
	}
	
	public List<Couple> getAllCouples(HttpServletRequest request){
		Optional<Object> userProfileId = jwtTokenService.getPropertyInJWTtokenOnRequest(request, "userProfileId");
		UUIDEditor uuidEditor = new UUIDEditor();
		uuidEditor.setAsText(userProfileId.get().toString());
		
		return this.coupleDao.selectAllCouples((UUID) uuidEditor.getValue());
	}
	
	public Optional<Couple> getCoupleById(UUID id, HttpServletRequest request){
		Optional<Object> userProfileId = jwtTokenService.getPropertyInJWTtokenOnRequest(request, "userProfileId");
		UUIDEditor uuidEditor = new UUIDEditor();
		uuidEditor.setAsText(userProfileId.get().toString());
		
		return this.coupleDao.selectCoupleById(id, (UUID) uuidEditor.getValue());
	}
	
	public Optional<Couple> getCoupleById(UUID id, UUID userProfileId){
		return this.coupleDao.selectCoupleById(id, userProfileId		);
	}
	
	public int deleteCoupleById(UUID id, HttpServletRequest request){
		Optional<Object> userProfileId = jwtTokenService.getPropertyInJWTtokenOnRequest(request, "userProfileId");
		UUIDEditor uuidEditor = new UUIDEditor();
		uuidEditor.setAsText(userProfileId.get().toString());
				
		return this.coupleDao.deleteCoupleById(id, (UUID) uuidEditor.getValue());
	}
	
	public int updateCoupleById(UUID id, CoupleInput coupleInput, HttpServletRequest request) {
		Optional<Object> userProfileId = jwtTokenService.getPropertyInJWTtokenOnRequest(request, "userProfileId");
		UUIDEditor uuidEditor = new UUIDEditor();
		uuidEditor.setAsText(userProfileId.get().toString());
		
		return this.coupleDao.updateCoupleById(id, coupleInput, (UUID) uuidEditor.getValue());
	}

	
}
