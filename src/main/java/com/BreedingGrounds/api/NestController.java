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

import com.BreedingGrounds.model.nest.Nest;
import com.BreedingGrounds.model.nest.NestInput;
import com.BreedingGrounds.service.NestService;

@RequestMapping("api/v1/nest")
@RestController
public class NestController {
	private final NestService nestService;

	@Autowired
	public NestController(NestService nestService) {
		this.nestService = nestService;
	}
	
	@PostMapping
	public int createCouple(@RequestBody NestInput nestInput, HttpServletRequest request) {
		return this.nestService.createNest(nestInput, request);
	}
	
	@GetMapping
	public List<Nest> selectAllCouples(HttpServletRequest request){
		return this.nestService.getAllNests(request);
	}
	
	@GetMapping(path = "{id}")
	public Optional<Nest> selectNestById(@PathVariable("id") UUID id, HttpServletRequest request) {
		return this.nestService.getNestById(id, request);
	}
	
	@DeleteMapping(path = "{id}")
	public void deleteNestById(@PathVariable("id") UUID id, HttpServletRequest request) {
		this.nestService.deleteNestById(id, request);
	}
	
	@PutMapping(path = "{id}")
	public void updateNestById(@PathVariable("id") UUID id, @RequestBody NestInput nestInput, HttpServletRequest request) {
		this.nestService.updateNestById(id, nestInput, request);
	}
	
}
