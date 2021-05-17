package com.BreedingGrounds.api;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BreedingGrounds.model.ResponseObject;
import com.BreedingGrounds.model.user.Login;
import com.BreedingGrounds.model.user.User;
import com.BreedingGrounds.model.user.UserProfile;
import com.BreedingGrounds.service.UserProfileService;

@RequestMapping("api/v1/userprofile")
@RestController
public class UserProfileController {

	private final UserProfileService userProfileService;
	
	public UserProfileController(UserProfileService userProfileService) {
		this.userProfileService = userProfileService;
	}
	
	@PostMapping
	public void addUserProfile(@Validated @NonNull @RequestBody UserProfile userProfile) {
		this.getUserProfileService().addUserProfile(userProfile);
	}
	
	@PostMapping(path = "login")
	public ResponseObject loginProfile(@RequestBody Login userProfile) {
		return this.getUserProfileService().loginUserProfile(userProfile);
	}
	
	@GetMapping(value = "/logout/{id}")
	public boolean logout(@PathVariable UUID id) {
		return this.getUserProfileService().logout(id);
	}
	
//	@GetMapping
//	public List<User> getAllUserProfile(){
//		return this.getUserProfileService().getAllUserProfile();
//	}
	
	@GetMapping
	public User getMyUserProfile(HttpServletRequest request){
		return this.getUserProfileService().getMyUserProfile(request);
	}
	
	@GetMapping(path = "/{id}")
	public Object getUserProfileById(@PathVariable("id") UUID id){
		return this.getUserProfileService().getUserProfileById(id);
	}
	
	@DeleteMapping(path = "{id}")
	public void deleteUserProfileById(@PathVariable("id") UUID id) {
		this.getUserProfileService().deleteUserProfileById(id);
	}
	
	@PutMapping(path = "{id}")
	public void updateUserProfileById(@PathVariable("id") UUID id, @RequestBody UserProfile userProfile) {
		this.getUserProfileService().updateUserProfileById(id, userProfile);
	}
	
	@PutMapping(path = "password/{id}")
	public void updatePasswordById(@PathVariable("id") UUID id, @RequestBody UserProfile userProfile) {
		this.getUserProfileService().updatePasswordById(id, userProfile);
	}
	
	public UserProfileService getUserProfileService() {
		return userProfileService;
	}
	
}
