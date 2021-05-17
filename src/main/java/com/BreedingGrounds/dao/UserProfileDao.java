package com.BreedingGrounds.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.BreedingGrounds.model.user.User;
import com.BreedingGrounds.model.user.UserProfile;

@Repository("userProfile")
public interface UserProfileDao {
	int insertUserProfile(UUID id, UserProfile userProfile);
	
	default int addUserProfile(UserProfile userProfile) {
		UUID id = UUID.randomUUID();
		return insertUserProfile(id, userProfile);
	}
	
	List<User> selectAllUserProfile();
	
	Optional<User> selectUserProfileById(UUID id);

	Optional<User> getUserProfileByEmail(String email);
	
	int deleteUserProfileById(UUID  id);
	
	int updateUserProfileById(UUID id, UserProfile userProfile);
	
	int updatePasswordById(UUID id, UserProfile userProfile);
	
	int enableUserProfile(String email, boolean enabled);
	
	int updateSessionById(UUID id, UUID sessionId);

}
