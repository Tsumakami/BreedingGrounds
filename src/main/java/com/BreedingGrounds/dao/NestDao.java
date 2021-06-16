package com.BreedingGrounds.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.BreedingGrounds.model.nest.Nest;
import com.BreedingGrounds.model.nest.NestInput;

@Repository("nest")
public interface NestDao {
	int createNest(UUID id, NestInput nestInput, UUID userProfileId);
	
	default int insertNest(NestInput nestInput, UUID userProfileId) {
		UUID id = UUID.randomUUID();
		return this.createNest(id, nestInput, userProfileId);
	}
	
	List<Nest> selectAllNests(UUID userProfileId);
	
	Optional<Nest> selectNestById(UUID id, UUID userProfileId);
	
	int deleteNestById(UUID  id, UUID userProfileId);
	
	int updateNestById(UUID id, NestInput nestInput, UUID userProfileId);
}
