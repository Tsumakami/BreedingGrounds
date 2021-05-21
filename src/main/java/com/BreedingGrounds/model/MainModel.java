package com.BreedingGrounds.model;

import java.util.UUID;

public class MainModel {
	private UUID userProfileId;

	public MainModel(UUID userProfileId) {
		this.userProfileId = userProfileId;
	}
	
	public UUID getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(UUID userProfileId) {
		this.userProfileId = userProfileId;
	}
	
	
}
