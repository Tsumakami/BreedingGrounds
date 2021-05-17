package com.BreedingGrounds.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GenericService {
	public final Logger logger;
	
	public GenericService() {
		this.logger = LoggerFactory.getLogger(this.getClass());
	}
}
