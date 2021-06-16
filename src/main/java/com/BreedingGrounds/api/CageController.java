package com.BreedingGrounds.api;

import java.util.List;
import java.util.Optional;
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

import com.BreedingGrounds.model.cage.Cage;
import com.BreedingGrounds.model.cage.CageInput;
import com.BreedingGrounds.service.CageService;

@RequestMapping("api/v1/cage")
@RestController
public class CageController {
	private final CageService cageService;

	@Autowired
	public CageController(CageService cageService) {
		this.cageService = cageService;
	}
	
	@PostMapping
	public int createCage(@RequestBody CageInput cageInput, HttpServletRequest request) {
		return this.cageService.createCage(cageInput, request);
	}
	
	@GetMapping
	public List<Cage> selectAllCages(HttpServletRequest request){
		return this.cageService.getAllCages(request);
	}
	
	@GetMapping(path = "{id}")
	public Optional<Cage> selectCageById(@PathVariable("id") UUID id, HttpServletRequest request) {
		return this.cageService.getCageById(id, request);
	}
	
	@DeleteMapping(path = "{id}")
	public void deleteCageById(@PathVariable("id") UUID id, HttpServletRequest request) {
		this.cageService.deleteCageById(id, request);
	}
	
	@PutMapping(path = "{id}")
	public void updateCageById(@PathVariable("id") UUID id, @RequestBody CageInput cageInput, HttpServletRequest request) {
		this.cageService.updateCageById(id, cageInput, request);
	}
	
}
