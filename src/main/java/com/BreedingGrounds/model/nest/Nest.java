package com.BreedingGrounds.model.nest;

import java.util.UUID;

import com.BreedingGrounds.model.couple.Couple;
import com.BreedingGrounds.model.user.UserIdModel;

public class Nest extends UserIdModel {
	private UUID id;
	private String displayName;
	private String description;
	private Couple couple;
	
	public Nest(UUID id, String displayName, String description, Couple couple, UUID userProfileId) {
		super(userProfileId);
		
		this.id = id;
		this.displayName = displayName;
		this.description = description;
		this.couple = couple;
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

	public Couple getCouple() {
		return couple;
	}

	public void setCouple(Couple couple) {
		this.couple = couple;
	}
	
}
