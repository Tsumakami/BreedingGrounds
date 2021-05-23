package com.BreedingGrounds.model;

import java.util.List;
import java.util.UUID;

import com.BreedingGrounds.model.birds.Bird;
import com.BreedingGrounds.model.couple.Couple;
import com.BreedingGrounds.model.nest.Nest;
import com.BreedingGrounds.model.user.UserIdModel;

public class Cage extends UserIdModel{
	private UUID id;
	private String displayName;
	private String description;
	private List<Nest> nests;
	private List<Bird> birds;
	private List<Couple> couple;
	
	public Cage(UUID id, String displayName, String description,
			List<Nest> nest, List<Bird> birds, List<Couple> couple,
			UUID userProfileId) {
		
		super(userProfileId);
		this.displayName = displayName;
		this.description = description;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Nest> getNests() {
		return nests;
	}

	public void setNests(List<Nest> nests) {
		this.nests = nests;
	}

	public List<Bird> getBirds() {
		return birds;
	}

	public void setBirds(List<Bird> birds) {
		this.birds = birds;
	}

	public List<Couple> getCouple() {
		return couple;
	}

	public void setCouple(List<Couple> couple) {
		this.couple = couple;
	}
	
}
