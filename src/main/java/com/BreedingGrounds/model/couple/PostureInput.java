package com.BreedingGrounds.model.couple;

import java.util.Date;
import java.util.UUID;

public class PostureInput {
	private Date dataPostura;
	private Date dataIncubation;
	private UUID coupleId;
	
	public PostureInput(Date dataPostura, Date dataIncubation, UUID coupleId) {
		this.dataPostura = dataPostura;
		this.dataIncubation = dataIncubation;
		this.setCoupleId(coupleId);
	}
		
	public Date getDataPostura() {
		return dataPostura;
	}
	
	public void setDataPostura(Date dataPostura) {
		this.dataPostura = dataPostura;
	}
	
	public Date getDataIncubation() {
		return dataIncubation;
	}
	
	public void setDataIncubation(Date dataIncubation) {
		this.dataIncubation = dataIncubation;
	}

	public UUID getCoupleId() {
		return coupleId;
	}

	public void setCoupleId(UUID coupleId) {
		this.coupleId = coupleId;
	}
	
	
}
