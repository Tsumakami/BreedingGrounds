package com.BreedingGrounds.model.user;

import java.util.UUID;

public class UserIdModel {
	private UUID userProfileId;

	public UserIdModel(UUID userProfileId) {
		this.userProfileId = userProfileId;
	}
	
	public UUID getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(UUID userProfileId) {
		this.userProfileId = userProfileId;
	}
	
	
}
