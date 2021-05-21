package com.BreedingGrounds.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.BreedingGrounds.model.birds.BirdAllInfo;
import com.BreedingGrounds.model.birds.BirdInput;

@Repository("bird")
public interface BirdDao {

	int insertBird(UUID id, BirdInput birdInput, UUID userProfileId);
	
	default int insertBird(BirdInput birdInput, UUID userProfileId) {
		UUID id = UUID.randomUUID();
		return this.insertBird(id, birdInput, userProfileId);
	}
	
	List<BirdAllInfo> selectAllBirds(UUID userProfileId);
	
	Optional<BirdAllInfo> selectBirdById(UUID id, UUID userProfileId);
	
	int deleteBirdById(UUID  id, UUID userProfileId);
	
	int updateBirdById(UUID id, BirdInput birdInput, UUID userProfileId);
	
}
