package com.BreedingGrounds.model.user;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User extends UserProfile implements UserDetails, Serializable {
	
	private static final long serialVersionUID = 4397127571798450670L;
	
	private AppUserRole appUserRole;
	private Boolean locked;
	private Boolean enabled;
	private UUID sessionId;
	
	private Boolean credentialNonExpired = true;
	private Boolean accountNonExpired = true;
	
	User(UUID id, String name, String cpf, String email, String password, Date birthDate,
			char gender) {
		super(id, name, cpf, email, password, birthDate, gender);
	}
	
	public User(UUID id, String name, String cpf, String email, String password, Date birthDate,
			char gender, boolean enabled, boolean locked, AppUserRole appUserRole, UUID sessionId) {
		super(id, name, cpf, email, password, birthDate, gender);
		this.enabled = enabled;
		this.locked = locked;
		this.appUserRole = appUserRole;
		this.sessionId = sessionId;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRole.name());
		return Collections.singletonList(authority);
	}

	@Override
	public String getUsername() {
		return super.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public UUID getSessionId() {
		return sessionId;
	}

	public void setSessionId(UUID sessionId) {
		this.sessionId = sessionId;
	}

}
