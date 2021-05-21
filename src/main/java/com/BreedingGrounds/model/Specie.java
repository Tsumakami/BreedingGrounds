package com.BreedingGrounds.model;

import java.util.List;
import java.util.UUID;

public class Specie extends MainModel {
	private UUID id;
	private String name;
	private List<String> breed;
	private int incubationPeriod;
	
	public Specie(UUID id, String name, List<String> breed, int incubationPeriod, UUID userProfileId) {
		super(userProfileId);
		
		this.id = id;
		this.name = name;
		this.breed = breed;
		this.incubationPeriod = incubationPeriod;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getBreed() {
		return breed;
	}

	public void setBreed(List<String> breed) {
		this.breed = breed;
	}

	public int getIncubationPeriod() {
		return incubationPeriod;
	}

	public void setIncubationDate(int incubationPeriod) {
		this.incubationPeriod = incubationPeriod;
	}

	@Override
	public String toString() {
		return "Specie [id=" + id + ", name=" + name + ", breed=" + breed + ", incubationPeriod=" + incubationPeriod
				+ ", userProfileId=" + getUserProfileId() + "]";
	}

	
}
