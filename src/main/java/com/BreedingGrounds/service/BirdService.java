package com.BreedingGrounds.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.BreedingGrounds.dao.BirdDao;
import com.BreedingGrounds.model.birds.BirdAllInfo;
import com.BreedingGrounds.model.birds.BirdInput;

@Service
public class BirdService {
	private final BirdDao birdDao;
	
	@Autowired
	public BirdService(@Qualifier("bird") BirdDao birdDao) {
		this.birdDao = birdDao;
	}
	
	public int addBird(BirdInput birdInput) {
		return this.getBirdDao().insertBird(birdInput);
	}
	
	public List<BirdAllInfo> getAllBirds(){
		return this.getBirdDao().selectAllBirds();
	}
	
	public Optional<BirdAllInfo> getBirdById(UUID id){
		return this.getBirdDao().selectBirdById(id);
	}
	
	public int deleteBirdById(UUID id){
		return this.getBirdDao().deleteBirdById(id);
	}
	
	public int updateSpecieById(UUID id, BirdInput birdInput) {
		return this.getBirdDao().updateBirdById(id, birdInput);
	}

	public BirdDao getBirdDao() {
		return birdDao;
	}
}
