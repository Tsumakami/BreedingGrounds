package com.BreedingGrounds.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.BreedingGrounds.model.Specie;

@Repository("specie")
public interface SpecieDao {

	int insertSpecie(UUID id,Specie specie);
	
	
	default int insertSpecie(Specie specie) {
		UUID id = UUID.randomUUID();
		return insertSpecie(id, specie);
	}

	List<Specie> selectAllSpecie();
	
	Optional<Specie> selectSpecieById(UUID id);
	
	int deleteSpecieById(UUID  id);
	
	int updateSpecieById(UUID id, Specie specie);
}
