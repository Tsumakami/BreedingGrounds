package com.BreedingGrounds.model.birds;

import java.util.Date;
import java.util.UUID;

public class Bird {
	private UUID id;
	private String washer;
	private Date birthDate;
	private char gender;
	private String color;
	private String breed;
	private String factors;
	private String portation;
	private Date dateAcquisition;
	private Date dateDeath;
	private String description;
	private UUID specieId;
	
	public Bird(UUID id, String washer, Date birthDate, char gender, String color,
			String breed, String factors, String portation, Date dateAcquisition, Date dateDeath, String description, UUID specieId) {
		this.id = id;
		this.washer = washer;
		this.birthDate = birthDate;
		this.gender = gender;
		this.color = color;
		this.breed = breed;
		this.factors = factors;
		this.portation = portation;
		this.dateAcquisition = dateAcquisition;
		this.dateDeath = dateDeath;
		this.description = description;
		this.setSpecieId(specieId);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getWasher() {
		return washer;
	}

	public void setWasher(String washer) {
		this.washer = washer;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public String getFactors() {
		return factors;
	}

	public void setFactors(String factors) {
		this.factors = factors;
	}

	public String getPortation() {
		return portation;
	}

	public void setPortation(String portation) {
		this.portation = portation;
	}

	public Date getDateAcquisition() {
		return dateAcquisition;
	}

	public void setDateAcquisition(Date dateAcquisition) {
		this.dateAcquisition = dateAcquisition;
	}

	public Date getDateDeath() {
		return dateDeath;
	}

	public void setDateDeath(Date dateDeath) {
		this.dateDeath = dateDeath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UUID getSpecieId() {
		return specieId;
	}
	
	public void setSpecieId(UUID specieId) {
		this.specieId = specieId;
	}
	
	@Override
	public String toString() {
		return "Bird [id=" + id + ", washer=" + washer + ", birthDate=" + birthDate + ", gender=" + gender + ", color="
				+ color + ", breed=" + breed + ", factors=" + factors + ", portation=" + portation
				+ ", dateAcquisition=" + dateAcquisition + ", dateDeath=" + dateDeath + ", description=" + description
				+ ", specieId=" + specieId + "]";
	}


}
