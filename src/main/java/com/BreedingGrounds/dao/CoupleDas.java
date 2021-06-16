package com.BreedingGrounds.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.BreedingGrounds.model.birds.BirdAllInfo;
import com.BreedingGrounds.model.couple.Couple;
import com.BreedingGrounds.model.couple.CoupleInput;
import com.BreedingGrounds.service.BirdService;
import com.BreedingGrounds.service.GenericService;

@Repository("couple")
public class CoupleDas extends GenericService implements CoupleDao {
	
	private final JdbcTemplate jdbcTemplate;
	private final BirdService birdService;
	
	@Autowired
	public CoupleDas(JdbcTemplate jdbcTemplate, BirdService birdService) {
		this.jdbcTemplate = jdbcTemplate;
		this.birdService = birdService;
	}
	
	@Override
	public int createCouple(UUID id, CoupleInput coupleInput, UUID userProfileId) {
		final String sql = "INSERT INTO couple"
				+ " (id, male_bird, female_bird, user_profile_id) "
				+ "VALUES (?, ?, ?, ?)";
		int result = 0; 
		
		try {
						
			logger.debug("Create couple={} to userPerfil={}", coupleInput.toString(), userProfileId.toString());
						
			Object params[] = {
					id, 
					coupleInput.getMaleBirdId(),
					coupleInput.getFemaleBirdId(),
					userProfileId
			};

			result = this.jdbcTemplate.update(sql, params);
			
			logger.info("Insert Couple with success. Couple={}.", coupleInput.toString());
		} catch (Exception e) {
			logger.error("Fail to insert couple={}, error={}", coupleInput.toString(), e);
		}
		
		return result;
	}

	@Override
	public List<Couple> selectAllCouples(UUID userProfileId) {
		final String sql = "SELECT * FROM couple where user_profile_id = ?::uuid ";
		final ResultSetExtractor<List<Couple>> resultExtractor = coupleResultExtractor(userProfileId);
		
		try {
			logger.debug("Select all couples...");
			
			List<Couple> listCouples = jdbcTemplate.query(sql, resultExtractor, new Object[] { userProfileId.toString() } ); 
			
			logger.debug("Select all couples return {} couples.", listCouples.size());
			
			return listCouples;
			
		} catch (Exception e) {
			logger.error("Fail to select all usersProfile, error={}", e);
		}
		return null;
	}

	@Override
	public Optional<Couple> selectCoupleById(UUID id, UUID userProfileId) {
		final String sql = "SELECT * FROM couple WHERE id = ?::uuid and user_profile_id = ?::uuid";
		final ResultSetExtractor<List<Couple>> resultExtractor = coupleResultExtractor(userProfileId);
		Couple couple = null;
		
		try {
			logger.debug("Select couple by Id={}",id.toString());
			
			List<Couple> listCouples = jdbcTemplate
					.query(sql, resultExtractor, new Object[] {id.toString(), userProfileId.toString()}); 
			
			if(!listCouples.isEmpty()) { 
				couple = listCouples.get(0);
				logger.debug("Select couple by id={}, return {}.", id, couple.toString());
			}
			
		} catch (Exception e) {
			logger.error("Fail to select all couples, error={}", e);
		}
		
		return Optional.ofNullable(couple);
	}

	@Override
	public int deleteCoupleById(UUID id, UUID userProfileId) {
		final String sql = "DELETE FROM couple WHERE id = ?::uuid and user_profile_id = ?::uuid";
		int result = 0;
		
		try {
			logger.debug("Delete couple by id={}.", id.toString());
			
			Object params[] = {
					id.toString(),
					userProfileId.toString()
			};
			
			result = this.jdbcTemplate.update(sql, params);
			
			if(result == 1) {
				logger.debug("Delete couple with id={}, success.", id.toString());
			}else {
				logger.warn("Delete couple by id={}, return the folow result={}", id.toString(), result);
			}
		}catch (Exception e) {
			logger.error("Fail to delete couple by id={}, error={}", id.toString(), e);
		}
		
		return result;
	}

	@Override
	public int updateCoupleById(UUID id, CoupleInput coupleInput, UUID userProfileId) {
		final String sql = "UPDATE couple"
				+ " SET (male_bird, female_bird)"
				+ " = (?, ?)"
				+ " WHERE id = ?::uuid and user_profile_id = ?::uuid";
		
		int result = 0; 
		
		try {
			logger.debug("Update couple={}", coupleInput.toString());
			
			Object params[] = {
				coupleInput.getMaleBirdId(),
				coupleInput.getFemaleBirdId(),
				id.toString(),	
				userProfileId.toString()
			};

			result = this.jdbcTemplate.update(sql, params);
			
			logger.info("Update couple with success. Couple={}.", coupleInput.toString());
			
		} catch (Exception e) {
			logger.error("Fail to update couple={}, error=", coupleInput.toString(), e);
		}
		
		return result;
	}

	private ResultSetExtractor<List<Couple>> coupleResultExtractor(UUID userProfileId) {
		ResultSetExtractor<List<Couple>> resultExtractor = (resultSet) -> {
			List<Couple> couples = new ArrayList<Couple>();
			while(resultSet.next()){
				Couple couple = this.resultSetToCouple(resultSet, userProfileId);
				
				couples.add(couple);
			};
			
			return couples;
		};
		
		return resultExtractor;
		
	}

	private Couple resultSetToCouple(ResultSet resultSet, UUID userProfileId) {
		Couple couple = null;
		
		try {
			UUID id = (UUID) resultSet.getObject("id");
			UUID maleBirdId = (UUID) resultSet.getObject("male_bird");
			UUID femaleBirdId = (UUID) resultSet.getObject("female_bird");
			
			Optional<BirdAllInfo> maleBird = birdService.getBirdById(maleBirdId, userProfileId);
			Optional<BirdAllInfo> femaleBird = birdService.getBirdById(femaleBirdId, userProfileId);
			
			couple = new Couple(
					id, 
					maleBird.get(),
					femaleBird.get(),
					userProfileId
					);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return couple;
	}
	
}
