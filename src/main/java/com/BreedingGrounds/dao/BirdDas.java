package com.BreedingGrounds.dao;

import java.sql.Date;
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
import org.springframework.transaction.annotation.Transactional;

import com.BreedingGrounds.model.Specie;
import com.BreedingGrounds.model.birds.Bird;
import com.BreedingGrounds.model.birds.BirdAllInfo;
import com.BreedingGrounds.model.birds.BirdInput;
import com.BreedingGrounds.service.GenericService;
import com.BreedingGrounds.service.SpecieService;

@Repository("bird")
public class BirdDas extends GenericService implements BirdDao {
	
	private final JdbcTemplate jdbcTemplate;
	private final SpecieService specieService;
	
	@Autowired
	public BirdDas(JdbcTemplate jdbcTemplate, SpecieService specieService) {
		this.jdbcTemplate = jdbcTemplate;
		this.specieService = specieService;
	}
	
	@Override
	public int insertBird(UUID id, BirdInput birdInput, UUID userProfileId) {
		final String sql = "INSERT INTO bird"
				+ " (id, washer, birth_date, gender, specie_id, color, breed,"
				+ " factors, portation, date_acquisition, date_death, description, father, mother, user_profile_id) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		int result = 0; 
		
		try {
			birdInput.setId(id);
			
			logger.debug("Insert {}", birdInput.toString());
			
			Object params[] = {
					birdInput.getId(), 
					birdInput.getWasher(),
					birdInput.getBirthDate(),
					birdInput.getGender(),
					birdInput.getSpecieId(),
					birdInput.getColor(),
					birdInput.getBreed(),
					birdInput.getFactors(),
					birdInput.getPortation(),
					birdInput.getDateAcquisition(),
					birdInput.getDateDeath(),
					birdInput.getDescription(),
					birdInput.getFatherId(),
					birdInput.getMotherId(),
					userProfileId
			};

			result = this.getJdbcTemplate().update(sql, params);
			
			logger.info("Insert Success the {}.", birdInput.toString());
			
		} catch (Exception e) {
			logger.error("Fail to insert {}, error={}", birdInput.toString(), e.getStackTrace().toString());
		}
		
		return result;
	}

	@Override
	public List<BirdAllInfo> selectAllBirds(UUID userProfileId) {
		final String sql = "SELECT * FROM bird where user_profile_id = ?::uuid";
		final ResultSetExtractor<List<BirdAllInfo>> resultExtractor = birdResultExtractor(false);
		
		try {
			logger.debug("Select all birds...");
			
			List<BirdAllInfo> listBirds = getJdbcTemplate().query(sql, resultExtractor, new Object[] { userProfileId.toString() }); 
			
			logger.debug("Select all birds, return {} birds.", listBirds.size());
			
			return listBirds;
			
		} catch (Exception e) {
			logger.error("Fail to select all birds, error={}", e);
		}
		return null;
	}
	
	@Transactional
	@Override
	public Optional<BirdAllInfo> selectBirdById(UUID id, UUID userProfileId) {
		final String sql = "SELECT * FROM bird WHERE id = ?::uuid and user_profile_id = ?::uuid";
		final ResultSetExtractor<List<BirdAllInfo>> resultExtractor = birdResultExtractor(false);
		BirdAllInfo bird = null;
		
		try {
			logger.debug("Select bird by Id={}",id.toString());
			
			List<BirdAllInfo> listBirds = getJdbcTemplate()
					.query(sql, resultExtractor, new Object[] {id.toString(), userProfileId.toString()}); 
			
			if(!listBirds.isEmpty()) { 
				bird = listBirds.get(0);
				logger.debug("Select bird by id={}, return {}.", id, bird.toString());
			}
			
		} catch (Exception e) {
			logger.error("Fail to select all birds, error={}", e);
		}
		
		return Optional.ofNullable(bird);
	}
	
	@Override
	public int deleteBirdById(UUID id, UUID userProfileId) {
		final String sql = "DELETE FROM bird WHERE id = ?::uuid and user_profile_id = ?::uuid";
		int result = 0;
		
		try {
			logger.debug("Delete bird by id={}.", id.toString());
			
			Object params[] = {
					id.toString(),
					userProfileId.toString()
			};
			
			result = this.getJdbcTemplate().update(sql, params);
			
			if(result == 1) {
				logger.debug("Delete bird with id={}, success.", id.toString());
			}else {
				logger.warn("Delete bird by id={}, return the folow result={}", id.toString(), result);
			}
		}catch (Exception e) {
			logger.error("Fail to delete bird by id={}, error={}", id.toString(), e);
		}
		
		return result;
	}

	@Override
	public int updateBirdById(UUID id, BirdInput birdInput, UUID userProfileId) {
		final String sql = "UPDATE bird"
				+ " SET ( washer, birth_date, gender, specie_id, color, breed,"
				+ " factors, portation, date_acquisition, date_death, description, father, mother)"
				+ " = (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
				+ " WHERE id = ?::uuid and user_profile_id = ?::uuid";
		
		int result = 0; 
		
		try {
			birdInput.setId(id);
			
			logger.debug("Update {}", birdInput.toString());
			
			Object params[] = {
					birdInput.getWasher(),
					birdInput.getBirthDate(),
					birdInput.getGender(),
					birdInput.getSpecieId(),
					birdInput.getColor(),
					birdInput.getBreed(),
					birdInput.getFactors(),
					birdInput.getPortation(),
					birdInput.getDateAcquisition(),
					birdInput.getDateDeath(),
					birdInput.getDescription(),
					birdInput.getFatherId(),
					birdInput.getMotherId(),
					id.toString(),
					userProfileId.toString()
			};

			result = this.getJdbcTemplate().update(sql, params);
			
			logger.info("Update Success the {}.", birdInput.toString());
			
		} catch (Exception e) {
			logger.error("Fail to update {}, error=", birdInput.toString(), e);
		}
		
		return result;
	}
	
	public Optional<Bird> selectParentById(UUID id, UUID userProfileId) {
		final String sql = "SELECT * FROM bird WHERE id = ?::uuid and user_profile_id = ?::uuid";
		final ResultSetExtractor<List<BirdAllInfo>> resultExtractor = birdResultExtractor(false);
		
		Optional<Bird> bird = null;
		
		try {
			logger.debug("Select Parent Bird by id={}", id.toString());
			
			List<BirdAllInfo> listBirds = getJdbcTemplate().query(sql, resultExtractor, new Object[] {id.toString(), userProfileId.toString()}); 
			
			bird = Optional.of((Bird) listBirds.get(0));
			
			logger.debug("Select parent bird {}.", listBirds.get(0));
			
		} catch (Exception e) {
			logger.error("Fail to select parent bird, error={}", e);
		}
		return bird;
	}
	
	private ResultSetExtractor<List<BirdAllInfo>> birdResultExtractor(boolean isGenealogicTree) {
		ResultSetExtractor<List<BirdAllInfo>> resultExtractor = (resultSet) -> {
			List<BirdAllInfo> birds = new ArrayList<BirdAllInfo>();
			while(resultSet.next()){
				Bird bird = null;
				BirdAllInfo birdAllInfo = null;
				
				if(isGenealogicTree) {
					bird = this.resultSetToBird(resultSet);
					birdAllInfo = this.resultSetToBird(resultSet, bird);
				}else {
					bird = this.resultSetToBird(resultSet);
					birdAllInfo = this.resultSetToParentBird(resultSet, bird);
				}
				
				birds.add(birdAllInfo);
			};
			
			return birds;
		};
		
		return resultExtractor;
	}
	
	private Bird resultSetToBird(ResultSet resultSet) {
		Bird bird = null;
		
		try {
			UUID id = (UUID) resultSet.getObject("id");
			String washer = resultSet.getString("washer");
			Date birthDate = resultSet.getDate("birth_date");
			char gender = resultSet.getString("gender").charAt(0);
			String color = resultSet.getString("color");
			String breed = resultSet.getString("breed");
			String factors = resultSet.getString("factors");
			String portation = resultSet.getString("portation");
			Date dateAcquisition = resultSet.getDate("date_acquisition");
			Date dateDeath = resultSet.getDate("date_death");
			String description = resultSet.getString("description");
			UUID specieId = (UUID) resultSet.getObject("specie_id");
			UUID userProfileId = (UUID) resultSet.getObject("user_profile_id");
			
			bird = new Bird(id, washer, birthDate, gender, color,
					breed, factors, portation, dateAcquisition, dateDeath,
					description, specieId, userProfileId);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return bird;
	}
	
	private BirdAllInfo resultSetToBird(ResultSet resultSet, Bird bird) {
		BirdAllInfo birdAllInfo = new BirdAllInfo(bird); 
		
		try {
			UUID fatherId = (UUID) resultSet.getObject("father");
			UUID motherId = (UUID) resultSet.getObject("mother");
			
			Optional<BirdAllInfo> optionalFather = Optional.ofNullable(null);
			Optional<BirdAllInfo> optionalMother = Optional.ofNullable(null);
			Optional<Specie> specie = Optional.ofNullable(null);
			
			specie = this.getSpecieService().getSpecieById(bird.getSpecieId(), bird.getUserProfileId());
			birdAllInfo.setSpecie(specie);
			
			if(fatherId != null) {
				optionalFather = this.selectBirdById(fatherId, bird.getUserProfileId());
				birdAllInfo.setFather(optionalFather);
			}
			
			if(motherId != null) {
				optionalMother = this.selectBirdById(motherId, bird.getUserProfileId());
				birdAllInfo.setMother(optionalMother);
			}
			
			
			
		} catch (Exception e) {
			logger.error("Fail to convert parent birds in Bird. Error={}", e);
		}
		
		return birdAllInfo;
	}
	
	private BirdAllInfo resultSetToParentBird(ResultSet resultSet, Bird bird) {
		BirdAllInfo birdAllInfo = new BirdAllInfo(bird);
		
		try {
			UUID fatherId = (UUID) resultSet.getObject("father");
			UUID motherId = (UUID) resultSet.getObject("mother");
			
			Optional<Specie> optionalSpecie = Optional.ofNullable(null);
			
			optionalSpecie = this.getSpecieService().getSpecieById(bird.getSpecieId(), bird.getUserProfileId());
			birdAllInfo.setSpecie(optionalSpecie);

			if(fatherId != null) {
				BirdAllInfo father = this.mountParentBird(fatherId, bird.getUserProfileId());
				
				birdAllInfo.setFather(Optional.of(father));
			}
			
			if(motherId != null) {
				BirdAllInfo mother = this.mountParentBird(motherId, bird.getUserProfileId());
				
				birdAllInfo.setMother(Optional.of(mother));
			}
			
		} catch (Exception e) {
			logger.error("Fail to convert ResultSet to parentBird. Error={}", e);
		}
		
		return birdAllInfo;
	}
	
	public BirdAllInfo mountParentBird(UUID birdId, UUID userProfileId) {
		BirdAllInfo parent = null;
		
		Optional<Bird> optionalParent = this.selectParentById(birdId, userProfileId);
		
		Optional<Specie> specieParent = this.getSpecieService().getSpecieById(optionalParent.get().getSpecieId(), userProfileId);
		
		parent = new BirdAllInfo(optionalParent.get());
		parent.setSpecie(specieParent);
		
		return parent;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public SpecieService getSpecieService() {
		return specieService;
	}

}
