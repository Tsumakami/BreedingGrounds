package com.BreedingGrounds.model.nest;

import java.util.UUID;

public class NestInput {
	private UUID id;
	private String displayName;
	private String description;
	private UUID coupleId;
	
	public NestInput(UUID id, String displayName, String description, UUID coupleId) {
		this.id = id;
		this.displayName = displayName;
		this.description = description;
		this.coupleId = coupleId;
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

	public UUID getCoupleId() {
		return coupleId;
	}

	public void setCoupleId(UUID coupleId) {
		this.coupleId = coupleId;
	}
	
}
