package com.BreedingGrounds.model.couple;

import java.util.Date;
import java.util.UUID;

import com.BreedingGrounds.model.user.UserIdModel;

public class Posture extends UserIdModel {
	private UUID id;
	private Date postureDate;
	private Date incubationDate;
	private Date prevBirthDate;
	private UUID coupleId;
	
	public Posture(UUID userProfileId, UUID id, Date postureDate, Date incubationDate, Date prevBirthDate, UUID coupleId) {
		super(userProfileId);
		this.id = id;
		this.postureDate = postureDate;
		this.incubationDate = incubationDate;
		this.prevBirthDate = prevBirthDate;
		this.coupleId = coupleId;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Date getPostureDate() {
		return postureDate;
	}

	public void setPostureDate(Date postureDate) {
		this.postureDate = postureDate;
	}

	public Date getIncubationDate() {
		return incubationDate;
	}

	public void setIncubationDate(Date incubationDate) {
		this.incubationDate = incubationDate;
	}

	public Date getPrevBirthDate() {
		return prevBirthDate;
	}

	public void setPrevBirthDate(Date prevBirthDate) {
		this.prevBirthDate = prevBirthDate;
	}

	public UUID getCoupleId() {
		return coupleId;
	}

	public void setCoupleId(UUID coupleId) {
		this.coupleId = coupleId;
	}
	
}
