package com.BreedingGrounds.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.BreedingGrounds.model.birds.BirdAllInfo;
import com.BreedingGrounds.model.birds.BirdInput;

@Repository("bird")
public interface BirdDao {

	int insertBird(UUID id, BirdInput birdInput);
	
	default int insertBird(BirdInput birdInput) {
		UUID id = UUID.randomUUID();
		return this.insertBird(id, birdInput);
	}
	
	List<BirdAllInfo> selectAllBirds();
	
	Optional<BirdAllInfo> selectBirdById(UUID id);
	
	int deleteBirdById(UUID  id);
	
	int updateBirdById(UUID id, BirdInput birdInput);
	
}
