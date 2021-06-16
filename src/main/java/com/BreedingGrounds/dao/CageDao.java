package com.BreedingGrounds.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.BreedingGrounds.model.cage.Cage;
import com.BreedingGrounds.model.cage.CageInput;

@Repository("cage")
public interface CageDao {
	int createCage(UUID id, CageInput cageInput, UUID userProfileId);
	
	default int insertCage(CageInput cageInput, UUID userProfileId) {
		UUID id = UUID.randomUUID();
		return this.createCage(id, cageInput, userProfileId);
	}
	
	List<Cage> selectAllCages(UUID userProfileId);
	
	Optional<Cage> selectCageById(UUID id, UUID userProfileId);
	
	int deleteCageById(UUID  id, UUID userProfileId);
	
	int updateCageById(UUID id, CageInput cageInput, UUID userProfileId);
}
