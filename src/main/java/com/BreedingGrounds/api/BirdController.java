package com.BreedingGrounds.api;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BreedingGrounds.model.birds.BirdAllInfo;
import com.BreedingGrounds.model.birds.BirdInput;
import com.BreedingGrounds.service.BirdService;

@RequestMapping("api/v1/bird")
@RestController
public class BirdController {
	private final BirdService birdService;
	
	public BirdController(BirdService birdService) {
		this.birdService = birdService;
	}
	
	@PostMapping
	public void addBird(@RequestBody BirdInput birdInput, HttpServletRequest request) {
		this.getBirdService().addBird(birdInput, request);
	}
	
	@GetMapping
	public List<BirdAllInfo> selectAllBirds(HttpServletRequest request){
		return this.getBirdService().getAllBirds(request);
	}
	
	@GetMapping(path = "{id}")
	public Optional<BirdAllInfo> selectBirdById(@PathVariable("id") UUID id, HttpServletRequest request) {
		return this.getBirdService().getBirdById(id, request);
	}
	
	@DeleteMapping(path = "{id}")
	public void deleteBirdById(@PathVariable("id") UUID id, HttpServletRequest request) {
		this.getBirdService().deleteBirdById(id, request);
	}
	
	@PutMapping(path = "{id}")
	public void updateBirdById(@PathVariable("id") UUID id, @RequestBody BirdInput birdInput, HttpServletRequest request) {
		this.getBirdService().updateBirdById(id, birdInput, request);
	}
	
	public BirdService getBirdService() {
		return birdService;
	}
}
