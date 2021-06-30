package com.BreedingGrounds.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.BreedingGrounds.model.Specie;
import com.BreedingGrounds.model.couple.Couple;
import com.BreedingGrounds.model.couple.Posture;
import com.BreedingGrounds.model.couple.PostureInput;
import com.BreedingGrounds.model.nest.Nest;
import com.BreedingGrounds.service.CoupleService;
import com.BreedingGrounds.service.GenericService;
import com.BreedingGrounds.service.SpecieService;

@Repository("posture")
public class PostureDas extends GenericService implements PostureDao {
	private final JdbcTemplate jdbcTemplate;
	private final CoupleService coupleService;
	private final SpecieService specieService;
	
	@Autowired
	public PostureDas(JdbcTemplate jdbcTemplate, CoupleService coupleService, SpecieService specieService) {
		this.jdbcTemplate = jdbcTemplate;
		this.coupleService = coupleService;
		this.specieService = specieService;
	}
	
	
	@Override
	public int createPosture(UUID id, PostureInput postureInput, UUID userProfileId) {
		final String sql = "INSERT INTO posture"
				+ " (id, date_posture, date_incubation, couple_id) "
				+ "VALUES (?, ?, ?, ?)";
		int result = 0; 
		
		try {
						
			logger.debug("Create posture={} to coupleId={}", postureInput.toString(), postureInput.getCoupleId().toString());
						
			Object params[] = {
					id,
					postureInput.getDataPostura(),
					postureInput.getDataIncubation(),
					postureInput.getCoupleId()
			};

			result = this.jdbcTemplate.update(sql, params);
			
			logger.info("Insert posture with success. Posture={}.", postureInput.toString());
		} catch (Exception e) {
			logger.error("Fail to insert nest={}, error={}", postureInput.toString(), e);
		}
		
		return result;
	}



	@Override
	public List<Posture> selectAllPosturesByCoupleId(UUID coupleId, UUID userProfileId) {
		final String sql = "SELECT * FROM posture WHERE couple_id = ?::uuid";
		final ResultSetExtractor<List<Posture>> resultExtractor = postureResultExtractor(coupleId, userProfileId);
		List<Posture> listPosture = null;
		
		try {
			logger.debug("Select posture by coupleId={}", coupleId.toString());
			
			listPosture = jdbcTemplate
					.query(sql, resultExtractor, new Object[] {coupleId.toString()}); 
			
			logger.debug("Select all posture by coupleId={}, return {}.", coupleId, listPosture.toString());
			
		} catch (Exception e) {
			logger.error("Fail to select postures by coupleId={}, error={}", coupleId.toString(), e);
		}
		
		return listPosture;
	}



	@Override
	public Optional<Posture> selectPostureById(UUID id, UUID userProfileId) {
		return null;
	}



	@Override
	public int deletePostureById(UUID id) {
		final String sql = "DELETE FROM posture WHERE id = ?::uuid";
		int result = 0;
		
		try {
			logger.debug("Delete nest by id={}.", id.toString());
			
			Object params[] = {
					id.toString()
			};
			
			result = this.jdbcTemplate.update(sql, params);
			
			if(result == 1) {
				logger.debug("Delete posture with id={}, success.", id.toString());
			}else {
				logger.warn("Delete posture by id={}, return the folow result={}", id.toString(), result);
			}
		}catch (Exception e) {
			logger.error("Fail to delete posture by id={}, error={}", id.toString(), e);
		}
		
		return result;
	}



	@Override
	public int updatePostureById(UUID id, PostureInput postureInput) {
		final String sql = "UPDATE posture"
				+ " SET (date_posture, date_incubation)"
				+ " = (?, ?)"
				+ " WHERE id = ?::uuid";
		
		int result = 0; 
		
		try {
			logger.debug("Update posture={}", postureInput.toString());
			
			Object params[] = {
				postureInput.getDataPostura(),
				postureInput.getDataIncubation(),
				id
			};

			result = this.jdbcTemplate.update(sql, params);
			
			logger.info("Update posture with success. Posture={}.", postureInput.toString());
			
		} catch (Exception e) {
			logger.error("Fail to update nest={}, error=", postureInput.toString(), e);
		}
		
		return result;
	}
	
	

	private ResultSetExtractor<List<Posture>> postureResultExtractor(UUID coupleId, UUID userProfileId) {
		ResultSetExtractor<List<Posture>> resultExtractor = (resultSet) -> {
			List<Posture> postures = new ArrayList<Posture>();
			while(resultSet.next()){
				Posture posture = this.resultSetToPosture(resultSet, coupleId, userProfileId);
				
				postures.add(posture);
			};
			
			return postures;
		};
		
		return resultExtractor;
	}

	private Posture resultSetToPosture(ResultSet resultSet, UUID coupleId, UUID userProfileId) {
		Posture posture = null;
		
		try {
			UUID id = (UUID) resultSet.getObject("id");
			Date postureDate = resultSet.getDate("date_posture");
			Date dateIncubation = resultSet.getDate("date_incubation");
					
			Optional<Couple> couple = this.coupleService.getCoupleById(coupleId, userProfileId);
			UUID specieId = couple.get().getMaleBird().getSpecieId();
			
			Optional<Specie> specie = this.specieService.getSpecieById(specieId, userProfileId);
			
			int incubationPeriod = specie.get().getIncubationPeriod();
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateIncubation);
			cal.add(Calendar.DAY_OF_MONTH, incubationPeriod);
			
			Date prevBirthDate = cal.getTime();
			
			posture = new Posture(
					userProfileId,
					id,
					postureDate,
					dateIncubation,
					prevBirthDate,
					coupleId
				);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return posture;
	}



	
	
}
