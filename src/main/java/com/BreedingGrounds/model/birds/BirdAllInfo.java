package com.BreedingGrounds.model.birds;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import com.BreedingGrounds.model.Specie;

public class BirdAllInfo extends Bird {
	private Optional<Specie> specie;
	private Optional<BirdAllInfo> father;
	private Optional<BirdAllInfo> mother;
	
	public BirdAllInfo(UUID id, String washer, Date birthDate, char gender, Optional<Specie> specie, String color,
			String breed, String factors, String portation, Date dateAcquisition, Date dateDeath, String description, 
			Optional<BirdAllInfo> father, Optional<BirdAllInfo> mother) {
		super(id, washer, birthDate, gender, color, breed, factors, portation, dateAcquisition, dateDeath,
				description, specie.get().getId());
		this.specie = specie;
		this.father = father;
		this.mother = mother;
	}
	
	public BirdAllInfo(Bird bird) {
		super(bird.getId(), bird.getWasher(), bird.getBirthDate(), bird.getGender(),
				bird.getColor(), bird.getBreed(), bird.getFactors(), bird.getPortation(), bird.getDateAcquisition(),
				bird.getDateDeath(), bird.getDescription(), bird.getSpecieId());
	}
	
	public Optional<BirdAllInfo> getFather() {
		return father;
	}

	public void setFather(Optional<BirdAllInfo> optionalFather) {
		this.father = optionalFather;
	}

	public Optional<BirdAllInfo> getMother() {
		return mother;
	}

	public void setMother(Optional<BirdAllInfo> optionalMother) {
		this.mother = optionalMother;
	}

	public Optional<Specie> getSpecie() {
		return specie;
	}

	public void setSpecie(Optional<Specie> specie) {
		this.specie = specie;
	}

	@Override
	public String toString() {
		return "BirdAllInfo [id=" + super.getId() + ", washer=" + super.getWasher() + ", birthDate=" + super.getBirthDate() + ", gender=" + super.getGender() + ", color="
				+ super.getColor() + ", breed=" + super.getBreed() + ", factors=" + super.getFactors() + ", portation=" + super.getPortation()
				+ ", dateAcquisition=" + super.getDateAcquisition() + ", dateDeath=" + super.getDateDeath() + ", description=" + super.getDescription() 
				+ ", specie=" + specie + ", father=" + father + ", mother=" + mother + "]";
	}
	
}
