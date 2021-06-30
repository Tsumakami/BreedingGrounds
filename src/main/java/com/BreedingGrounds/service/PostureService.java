package com.BreedingGrounds.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.UUIDEditor;
import org.springframework.stereotype.Service;

import com.BreedingGrounds.dao.PostureDao;
import com.BreedingGrounds.model.couple.Posture;
import com.BreedingGrounds.model.couple.PostureInput;
import com.BreedingGrounds.model.nest.Nest;
import com.BreedingGrounds.model.nest.NestInput;

@Service
public class PostureService {
	private final PostureDao postureDao;
	private final JWTTokenService jwtTokenService;
	
	@Autowired
	public PostureService(PostureDao postureDao, JWTTokenService jwtTokenService) {
		this.postureDao = postureDao;
		this.jwtTokenService = jwtTokenService;
	}
	
	public int createPosture(PostureInput postureInput, HttpServletRequest request) {
		Optional<Object> userProfileId = jwtTokenService.getPropertyInJWTtokenOnRequest(request, "userProfileId");
		UUIDEditor uuidEditor = new UUIDEditor();
		uuidEditor.setAsText(userProfileId.get().toString());
		
		return this.postureDao.insertPosture(postureInput, (UUID) uuidEditor.getValue());
	}
	
	public List<Posture> getAllPosturesByCouple(UUID coupleId, HttpServletRequest request){
		Optional<Object> userProfileId = jwtTokenService.getPropertyInJWTtokenOnRequest(request, "userProfileId");
		UUIDEditor uuidEditor = new UUIDEditor();
		uuidEditor.setAsText(userProfileId.get().toString());
		
		return this.postureDao.selectAllPosturesByCoupleId(coupleId, (UUID) uuidEditor.getValue());
	}
	
	
	public int deletePostureById(UUID id){
		return this.postureDao.deletePostureById(id);
	}
	
	public int updatePostureById(UUID id, PostureInput postureInput, HttpServletRequest request) {
		return this.postureDao.updatePostureById(id, postureInput);
	}
	
}
