package com.BreedingGrounds.api;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
	public void addBird(@RequestBody BirdInput birdInput) {
		this.getBirdService().addBird(birdInput);
	}
	
	@GetMapping
	public List<BirdAllInfo> selectAllBirds(){
		return this.getBirdService().getAllBirds();
	}
	
	@GetMapping(path = "{id}")
	public Optional<BirdAllInfo> selectBirdById(@PathVariable("id") UUID id) {
		return this.getBirdService().getBirdById(id);
	}
	
	@DeleteMapping(path = "{id}")
	public void deleteBirdById(@PathVariable("id") UUID id) {
		this.getBirdService().deleteBirdById(id);
	}
	
	@PutMapping(path = "{id}")
	public void updateBirdById(@PathVariable("id") UUID id, @RequestBody BirdInput birdInput) {
		this.getBirdService().updateSpecieById(id, birdInput);
	}
	
	public BirdService getBirdService() {
		return birdService;
	}
}
