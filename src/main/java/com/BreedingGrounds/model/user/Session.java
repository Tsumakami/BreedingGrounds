package com.BreedingGrounds.model.user;

import java.util.Date;
import java.util.UUID;

public class Session {
	private UUID id;
	private String jwtToken;
	private Date createDate;
	private boolean valid = false;
	
	public Session(UUID id, String jwtToken, Date createDate, boolean valid) {
		this.id = id;
		this.jwtToken = jwtToken;
		this.createDate = createDate;
		this.valid = valid;
	}
	
	public Session(String jwtToken, Date createDate, boolean valid) {
		this.id = UUID.randomUUID();
		this.jwtToken = jwtToken;
		this.createDate = createDate;
		this.valid = valid;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	@Override
	public String toString() {
		return "Session [id=" + id + ", jwtToken=" + jwtToken + ", createDate=" + createDate + ", valid=" + valid + "]";
	}
	
}
