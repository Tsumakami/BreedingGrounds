package com.BreedingGrounds.api;

import java.util.List;
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

import com.BreedingGrounds.model.Specie;
import com.BreedingGrounds.service.SpecieService;

@RequestMapping("api/v1/specie")
@RestController
public class SpecieController {
	private final SpecieService specieService;

	public SpecieController(SpecieService specieService) {
		this.specieService = specieService;
	}
	
	@PostMapping
	public void addSpecie(@Validated @NonNull @RequestBody Specie specie, HttpServletRequest request) {
		this.getSpecieService().addSpecie(specie, request);
	}
	
	@GetMapping
	public List<Specie> getAllSpecies(HttpServletRequest request){
		return this.getSpecieService().getAllSpecies(request);
	}
	
	@GetMapping(path = "/{id}")
	public Object getSpecieById(@PathVariable("id") UUID id, HttpServletRequest request){
		return this.getSpecieService().getSpecieById(id, request);
	}
	
	@DeleteMapping(path = "{id}")
	public void deleteSpecieById(@PathVariable("id") UUID id, HttpServletRequest request) {
		this.getSpecieService().deleteSpecieById(id, request);
	}
	
	@PutMapping(path = "{id}")
	public void updateSpecieById(@PathVariable("id") UUID id, @RequestBody Specie specie, HttpServletRequest request) {
		getSpecieService().updateSpecieById(id, specie, request);
	}
	
	public SpecieService getSpecieService() {
		return specieService;
	}
}
