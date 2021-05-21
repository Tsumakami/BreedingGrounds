package com.BreedingGrounds.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.BreedingGrounds.model.Specie;

@Repository("specie")
public interface SpecieDao {

	int insertSpecie(UUID id,Specie specie, UUID userProfileId);
	
	
	default int insertSpecie(Specie specie, UUID userProfileId) {
		UUID id = UUID.randomUUID();
		return insertSpecie(id, specie, userProfileId);
	}

	List<Specie> selectAllSpecie(UUID userProfileId);
	
	Optional<Specie> selectSpecieById(UUID id, UUID userProfileId);
	
	int deleteSpecieById(UUID  id, UUID userProfileId);
	
	int updateSpecieById(UUID id, Specie specie, UUID userProfileId);
}
