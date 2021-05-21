package com.BreedingGrounds.model.couple;

import java.util.UUID;

public class CoupleInput {
	private UUID maleBirdId;
	private UUID femaleBirdId;
	
	CoupleInput(UUID maleBirdId, UUID femaleBirdId){
		this.maleBirdId = maleBirdId;
		this.femaleBirdId = femaleBirdId;
	}

	public UUID getMaleBirdId() {
		return maleBirdId;
	}

	public void setMaleBirdId(UUID maleBirdId) {
		this.maleBirdId = maleBirdId;
	}

	public UUID getFemaleBirdId() {
		return femaleBirdId;
	}

	public void setFemaleBirdId(UUID femaleBirdId) {
		this.femaleBirdId = femaleBirdId;
	}
	
}
