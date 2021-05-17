package com.BreedingGrounds.model.user;

import java.util.Date;
import java.util.UUID;

public class UserProfile {
	private UUID id;
	private String name;
	private String cpf;
	private String email;
	private String password;
	private Date birthDate;
	private char gender = 'M';
	
	public UserProfile(UUID id, String name, String cpf, String email, 
			String password, Date birthDate, char gender) {
		this.id = id;
		this.name = name;
		this.cpf = cpf;
		this.email = email;
		this.password = password;
		this.birthDate = birthDate;
		this.gender = gender;
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
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
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

	@Override
	public String toString() {
		return "UserProfile [id=" + id + ", name=" + name + ", cpf=" + cpf + ", email=" + email + ", password="
				+ password + ", birthDate=" + birthDate + ", gender=" + gender + "]";
	}
	
}
