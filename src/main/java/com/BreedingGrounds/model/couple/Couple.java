package com.BreedingGrounds.model.couple;

import java.util.UUID;

import com.BreedingGrounds.model.MainModel;
import com.BreedingGrounds.model.birds.Bird;

public class Couple extends MainModel {
	private UUID id;
	private Bird maleBird;
	private Bird femaleBird;
	
	public Couple(UUID id, Bird maleBird, Bird femaleBird, UUID userProfileId) {
		super(userProfileId);
		
		this.id = id;
		this.maleBird = maleBird;
		this.femaleBird = femaleBird;
	}
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public Bird getMaleBird() {
		return maleBird;
	}
	public void setMaleBird(Bird maleBird) {
		this.maleBird = maleBird;
	}
	public Bird getFemaleBird() {
		return femaleBird;
	}
	public void setFemaleBird(Bird femaleBird) {
		this.femaleBird = femaleBird;
	}

	@Override
	public String toString() {
		return "Couple [id=" + id + ", maleBird=" + maleBird + ", femaleBird=" + femaleBird + ", userProfileId="
				+ getUserProfileId() + "]";
	}
	
	
		
}
