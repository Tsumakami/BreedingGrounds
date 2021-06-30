package com.BreedingGrounds.api;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BreedingGrounds.model.couple.Posture;
import com.BreedingGrounds.model.couple.PostureInput;
import com.BreedingGrounds.service.PostureService;

@RequestMapping("api/v1/posture")
@RestController
public class PostureController {
	private final PostureService postureService;

	@Autowired
	public PostureController(PostureService postureService) {
		this.postureService = postureService;
	}
	
	@PostMapping
	public int createPosture(@RequestBody PostureInput postureInput, HttpServletRequest request) {
		return this.postureService.createPosture(postureInput, request);
	}
	
	@GetMapping(path = "{id}")
	public List<Posture> selectAllPostureByCoupleId(@PathVariable("id")UUID coupleId, HttpServletRequest request){
		return this.postureService.getAllPosturesByCouple(coupleId, request);
	}
	
	@DeleteMapping(path = "{id}")
	public void deleteNestById(@PathVariable("id") UUID id) {
		this.postureService.deletePostureById(id);
	}
	
	@PutMapping(path = "{id}")
	public void updatePostureById(@PathVariable("id") UUID id, @RequestBody PostureInput postureInput, 
			HttpServletRequest request) {
		this.postureService.updatePostureById(id, postureInput, request);
	}
	
}
