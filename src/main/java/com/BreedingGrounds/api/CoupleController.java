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

import com.BreedingGrounds.model.couple.Couple;
import com.BreedingGrounds.model.couple.CoupleInput;
import com.BreedingGrounds.model.couple.Posture;
import com.BreedingGrounds.model.couple.PostureInput;
import com.BreedingGrounds.service.CoupleService;

@RequestMapping("api/v1/couple")
@RestController
public class CoupleController {
	private final CoupleService coupleService;

	@Autowired
	public CoupleController(CoupleService coupleService) {
		this.coupleService = coupleService;
	}
	
	@PostMapping
	public int createCouple(@RequestBody CoupleInput coupleInput, HttpServletRequest request) {
		return this.coupleService.createCouple(coupleInput, request);
	}
	
	@GetMapping
	public List<Couple> selectAllCouples(HttpServletRequest request){
		return this.coupleService.getAllCouples(request);
	}
	
	@GetMapping(path = "{id}")
	public Optional<Couple> selectCoupleById(@PathVariable("id") UUID id, HttpServletRequest request) {
		return this.coupleService.getCoupleById(id, request);
	}
	
	@DeleteMapping(path = "{id}")
	public void deleteCoupleById(@PathVariable("id") UUID id, HttpServletRequest request) {
		this.coupleService.deleteCoupleById(id, request);
	}
	
	@PutMapping(path = "{id}")
	public void updateCoupleById(@PathVariable("id") UUID id, @RequestBody CoupleInput CoupleInput, HttpServletRequest request) {
		this.coupleService.updateCoupleById(id, CoupleInput, request);
	}
	
}
