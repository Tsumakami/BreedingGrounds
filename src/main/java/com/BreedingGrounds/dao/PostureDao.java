package com.BreedingGrounds.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.BreedingGrounds.model.couple.Posture;
import com.BreedingGrounds.model.couple.PostureInput;

@Repository("posture")
public interface PostureDao {
	int createPosture(UUID id, PostureInput postureInput, UUID userProfileId);
	
	default int insertPosture(PostureInput postureInput, UUID userProfileId) {
		UUID id = UUID.randomUUID();
		return this.createPosture(id, postureInput, userProfileId);
	}
	
	List<Posture> selectAllPosturesByCoupleId(UUID coupleId, UUID userProfileId);
	
	Optional<Posture> selectPostureById(UUID id, UUID userProfileId);
	
	int deletePostureById(UUID  id);
	
	int updatePostureById(UUID id, PostureInput postureInput);
}
