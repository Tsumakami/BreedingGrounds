package com.BreedingGrounds.model.cage;

import java.util.List;
import java.util.UUID;

public class CageInput {
	private String displayName;
	private String description;
	private List<UUID> nests;
	private List<UUID> birds;
	private List<UUID> couples;
	
	public CageInput(String displayName, String description, List<UUID> nests, List<UUID> birds, List<UUID> couples) {
		this.displayName = displayName;
		this.description = description;
		this.nests = nests;
		this.birds = birds;
		this.couples = couples;
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

	public List<UUID> getNests() {
		return nests;
	}

	public void setNests(List<UUID> nests) {
		this.nests = nests;
	}

	public List<UUID> getBirds() {
		return birds;
	}

	public void setBirds(List<UUID> birds) {
		this.birds = birds;
	}

	public List<UUID> getCouples() {
		return couples;
	}

	public void setCouples(List<UUID> couples) {
		this.couples = couples;
	}

	@Override
	public String toString() {
		return "CageInput [displayName=" + displayName + ", description=" + description + ", nests=" + nests
				+ ", birds=" + birds + ", couples=" + couples + "]";
	}
	
}
