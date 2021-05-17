package com.BreedingGrounds.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.BreedingGrounds.dao.SpecieDao;
import com.BreedingGrounds.model.Specie;

@Service
public class SpecieService {
	
	private final SpecieDao specieDao;
	
	@Autowired
	public SpecieService(@Qualifier("specie") SpecieDao specieDao) {
		this.specieDao = specieDao;
	}
	
	public int addSpecie(Specie specie) {
		return this.getSpecieDao().insertSpecie(specie);
	}
	
	public List<Specie> getAllSpecies(){
		return this.getSpecieDao().selectAllSpecie();
	}
	
	public Optional<Specie> getSpecieById(UUID id){
		return this.getSpecieDao().selectSpecieById(id);
	}
	
	public int deleteSpecieById(UUID id){
		return this.getSpecieDao().deleteSpecieById(id);
	}
	
	public int updateSpecieById(UUID id, Specie specie) {
		return this.getSpecieDao().updateSpecieById(id, specie);
	}
	
	public SpecieDao getSpecieDao() {
		return specieDao;
	}
	
}
