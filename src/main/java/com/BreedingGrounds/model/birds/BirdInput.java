package com.BreedingGrounds.model.birds;

import java.util.Date;
import java.util.UUID;

public class BirdInput extends Bird{
	private UUID fatherId;
	private UUID motherId;
	
	public BirdInput(UUID id, String washer, Date birthDate, char gender, String color,
			String breed, String factors, String portation, Date dateAcquisition, Date dateDeath, String description,
			UUID specieId, UUID fatherId, UUID motherId, UUID userProfileId) {
		super(id, washer, birthDate, gender, color, breed, factors, portation, dateAcquisition, dateDeath,
				description, specieId, userProfileId);
		this.fatherId = fatherId;
		this.motherId = motherId;
	}

	public UUID getFatherId() {
		return fatherId;
	}

	public void setFatherId(UUID fatherId) {
		this.fatherId = fatherId;
	}

	public UUID getMotherId() {
		return motherId;
	}

	public void setMotherId(UUID motherId) {
		this.motherId = motherId;
	}

}