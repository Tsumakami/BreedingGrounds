package com.BreedingGrounds.api;

import java.util.List;
import java.util.UUID;

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
	public void addSpecie(@Validated @NonNull @RequestBody Specie specie) {
		this.getSpecieService().addSpecie(specie);
	}
	
	@GetMapping
	public List<Specie> getAllSpecies(){
		return this.getSpecieService().getAllSpecies();
	}
	
	@GetMapping(path = "/{id}")
	public Object getSpecieById(@PathVariable("id") UUID id){
		return this.getSpecieService().getSpecieById(id);
	}
	
	@DeleteMapping(path = "{id}")
	public void deleteSpecieById(@PathVariable("id") UUID id) {
		this.getSpecieService().deleteSpecieById(id);
	}
	
	@PutMapping(path = "{id}")
	public void updateSpecieById(@PathVariable("id") UUID id, @RequestBody Specie specie) {
		getSpecieService().updateSpecieById(id, specie);
	}
	
	public SpecieService getSpecieService() {
		return specieService;
	}
}
