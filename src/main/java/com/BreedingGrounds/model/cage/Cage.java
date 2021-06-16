package com.BreedingGrounds.model.cage;

import java.util.List;
import java.util.UUID;

import com.BreedingGrounds.model.birds.BirdAllInfo;
import com.BreedingGrounds.model.couple.Couple;
import com.BreedingGrounds.model.nest.Nest;
import com.BreedingGrounds.model.user.UserIdModel;

public class Cage extends UserIdModel{
	private UUID id;
	private String displayName;
	private String description;
	private List<Nest> nests;
	private List<BirdAllInfo> birds;
	private List<Couple> couples;
	
	public Cage(UUID id, String displayName, String description,
			List<Nest> nests, List<BirdAllInfo> birds, List<Couple> couples,
			UUID userProfileId) {
		
		super(userProfileId);
		this.id = id;
		this.displayName = displayName;
		this.description = description;
		this.nests = nests;
		this.birds = birds;
		this.couples = couples;
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

	public List<BirdAllInfo> getBirds() {
		return birds;
	}

	public void setBirds(List<BirdAllInfo> birds) {
		this.birds = birds;
	}

	public List<Couple> getCouples() {
		return couples;
	}

	public void setCouple(List<Couple> couples) {
		this.couples = couples;
	}
	
}
