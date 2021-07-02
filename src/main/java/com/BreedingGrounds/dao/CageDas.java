package com.BreedingGrounds.dao;

import java.sql.Array;
import java.sql.Connection;
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

import com.BreedingGrounds.model.birds.Bird;
import com.BreedingGrounds.model.birds.BirdAllInfo;
import com.BreedingGrounds.model.cage.Cage;
import com.BreedingGrounds.model.cage.CageInput;
import com.BreedingGrounds.model.couple.Couple;
import com.BreedingGrounds.model.nest.Nest;
import com.BreedingGrounds.service.BirdService;
import com.BreedingGrounds.service.CoupleService;
import com.BreedingGrounds.service.GenericService;
import com.BreedingGrounds.service.NestService;

@Repository("cage")
public class CageDas extends GenericService implements CageDao {

	private final JdbcTemplate jdbcTemplate;
	private final NestService nestService;
	private final BirdService birdService;
	private final CoupleService coupleService;
	
	@Autowired
	public CageDas(JdbcTemplate jdbcTemplate, NestService nestService,
			BirdService birdService, CoupleService coupleService) {
		this.jdbcTemplate = jdbcTemplate;
		this.nestService = nestService;
		this.birdService = birdService;
		this.coupleService = coupleService;
	}
	
	@Override
	public int createCage(UUID id, CageInput cageInput, UUID userProfileId) {
		final String sql = "INSERT INTO cage"
				+ " (id, display_name, description, nests, birds, couples, user_profile_id) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		int result = 0; 
		
		try {
						
			logger.debug("Create cage={} to userPerfil={}", cageInput.toString(), userProfileId.toString());
						
			CageInput cageInputRight = cageInputCorrector(cageInput, userProfileId); 
			
			Connection connection = this.jdbcTemplate.getDataSource().getConnection();
			
			Array nests = cageInputRight.getNests() != null ? 
					connection.createArrayOf("uuid", cageInputRight.getNests().toArray()) : null;
			Array birds = cageInputRight.getBirds() != null ? 
					connection.createArrayOf("uuid", cageInputRight.getBirds().toArray()) : null;
			Array couples = cageInputRight.getCouples() != null ?
					connection.createArrayOf("uuid", cageInputRight.getCouples().toArray()) : null;
			
			Object params[] = {
					id, 
					cageInputRight.getDisplayName(),
					cageInputRight.getDescription(),
					nests,
					birds,
					couples,
					userProfileId
			};
			
			result = this.jdbcTemplate.update(sql, params);
			
			logger.info("Insert Cage with success. cage={}.", cageInput.toString());
		} catch (Exception e) {
			logger.error("Fail to insert cage={}, error={}", cageInput.toString(), e);
		}
		
		return result;
	}

	@Override
	public List<Cage> selectAllCages(UUID userProfileId) {
		final String sql = "SELECT * FROM cage where user_profile_id = ?::uuid ";
		final ResultSetExtractor<List<Cage>> resultExtractor = cageResultExtractor(userProfileId);
		
		try {
			logger.debug("Select all cages...");
			
			List<Cage> listCages = jdbcTemplate.query(sql, resultExtractor, new Object[] { userProfileId.toString() } ); 
			
			logger.debug("Select all cages, return {} cages.", listCages.size());
			
			return listCages;
			
		} catch (Exception e) {
			logger.error("Fail to select all cages, error={}", e);
		}
		return null;
	}

	@Override
	public Optional<Cage> selectCageById(UUID id, UUID userProfileId) {
		final String sql = "SELECT * FROM cage WHERE id = ?::uuid and user_profile_id = ?::uuid";
		final ResultSetExtractor<List<Cage>> resultExtractor = cageResultExtractor(userProfileId);
		Cage cage = null;
		
		try {
			logger.debug("Select cage by Id={}", id.toString());
			
			List<Cage> listCages = jdbcTemplate
					.query(sql, resultExtractor, new Object[] {id.toString(), userProfileId.toString()}); 
			
			if(!listCages.isEmpty()) { 
				cage = listCages.get(0);
				logger.debug("Select cage by id={}, return {}.", id, cage.toString());
			}
			
		} catch (Exception e) {
			logger.error("Fail to select cage by id={}, error={}",id.toString(), e);
		}
		
		return Optional.ofNullable(cage);
	}

	@Override
	public int deleteCageById(UUID id, UUID userProfileId) {
		final String sql = "DELETE FROM cage WHERE id = ?::uuid and user_profile_id = ?::uuid";
		int result = 0;
		
		try {
			logger.debug("Delete cage by id={}.", id.toString());
			
			Object params[] = {
					id.toString(),
					userProfileId.toString()
			};
			
			result = this.jdbcTemplate.update(sql, params);
			
			if(result == 1) {
				logger.debug("Delete cage with id={}, success.", id.toString());
			}else {
				logger.warn("Delete cage by id={}, return the folow result={}", id.toString(), result);
			}
		}catch (Exception e) {
			logger.error("Fail to delete cage by id={}, error={}", id.toString(), e);
		}
		
		return result;
	}

	@Override
	public int updateCageById(UUID id, CageInput cageInput, UUID userProfileId) {
		final String sql = "UPDATE cage"
				+ " SET (display_name, description, nests, birds, couples)"
				+ " = (?, ?, ?, ?, ?)"
				+ " WHERE id = ?::uuid and user_profile_id = ?::uuid";
		
		int result = 0; 
		
		try {
			logger.debug("Update cage={}", cageInput.toString());
			
			CageInput cageInputRight = cageInputCorrector(cageInput, userProfileId); 
			
			Connection connection = this.jdbcTemplate.getDataSource().getConnection();
			Array nests = connection.createArrayOf("uuid", cageInputRight.getNests().toArray());
			Array birds = connection.createArrayOf("uuid", cageInputRight.getBirds().toArray());
			Array couples = connection.createArrayOf("uuid", cageInputRight.getCouples().toArray());
			
			Object params[] = {
				cageInput.getDisplayName(),
				cageInput.getDescription(),
				nests,
				birds,
				couples,
				id.toString(),	
				userProfileId.toString()
			};

			result = this.jdbcTemplate.update(sql, params);
			
			logger.info("Update cage with success. Cage={}.", cageInput.toString());
			
		} catch (Exception e) {
			logger.error("Fail to update cage={}, error=", cageInput.toString(), e);
		}
		
		return result;
	}
	
	private CageInput cageInputCorrector(CageInput cageInput, UUID userProfileId) {
		if(cageInput.getNests() != null && !cageInput.getNests().isEmpty()) {
			List<UUID> couplesList = new ArrayList<UUID>();
			for(UUID nestId : cageInput.getNests()) {
				Optional<Nest> nest = nestService.getNestById(nestId, userProfileId);
				if(nest.isPresent()) {
					if(nest.get().getCouple()!= null) {
						UUID coupleId = nest.get().getCouple().getId();
						if(cageInput.getCouples() != null && !cageInput.getCouples().contains(coupleId)) {
							cageInput.getCouples().add(coupleId);
						}else {
							couplesList.add(coupleId);
							cageInput.setCouples(couplesList);
						}
						
						Bird birdMale = nest.get().getCouple().getMaleBird();
						Bird birdFemale = nest.get().getCouple().getFemaleBird();
						
						if(!cageInput.getBirds().contains(birdMale.getId())) {
							cageInput.getBirds().add(birdMale.getId());
						}
						
						if(!cageInput.getBirds().contains(birdFemale.getId())) {
							cageInput.getBirds().add(birdFemale.getId());
						}
					}
				}
			}
		}
		
		if(cageInput.getCouples() != null && !cageInput.getCouples().isEmpty()) {
			for(UUID coupleId : cageInput.getCouples()) {
				Optional<Couple> couple = coupleService.getCoupleById(coupleId, userProfileId);
				if(couple.isPresent()) {
					Bird birdMale = couple.get().getMaleBird();
					Bird birdFemale = couple.get().getFemaleBird();
					
					if(!cageInput.getBirds().contains(birdMale.getId())) {
						cageInput.getBirds().add(birdMale.getId());
					}
					
					if(!cageInput.getBirds().contains(birdFemale.getId())) {
						cageInput.getBirds().add(birdFemale.getId());
					}
				}
			}
		}
		
		if(cageInput.getBirds() != null && !cageInput.getBirds().isEmpty()) {
			List<UUID> couplesIds = new ArrayList<UUID>();
			List<UUID> nestIds = new ArrayList<UUID>();
			
			for(UUID birdId : cageInput.getBirds()) {
				Optional<BirdAllInfo> bird = birdService.getBirdById(birdId, userProfileId);
				
				if(bird.isPresent()) {
					char gender = bird.get().getGender();
					
					Optional<Couple> couple = Optional.empty();
					if(gender == 'M') {
						couple = this.coupleService.getCoupleByMaleBirdId(bird.get().getId(), userProfileId);
					}else if(gender == 'F') {
						couple = this.coupleService.getCoupleByFemaleBirdId(bird.get().getId(), userProfileId);
					}
					
					if(couple.isPresent()) {
						boolean birdMaleInsertInCage = cageInput.getBirds().contains(couple.get().getMaleBird().getId());
						boolean birdFemaleInsertInCage = cageInput.getBirds().contains(couple.get().getFemaleBird().getId());
						
						if(birdMaleInsertInCage && birdFemaleInsertInCage) {
							if(couplesIds.isEmpty() || !couplesIds.contains(couple.get().getId()) && !cageInput.getCouples().contains(couple.get().getId())) {
								couplesIds.add(couple.get().getId());
							}
						}
					}					
				}	
			}
			
			if(!couplesIds.isEmpty()) {
				if(cageInput.getCouples() != null) {
					for(UUID coupleId : couplesIds) {
						if(!cageInput.getCouples().contains(coupleId)) {
							cageInput.getCouples().add(coupleId);						
						}
					}
				}else {
					cageInput.setCouples(couplesIds);
				}
			}
			
			if(cageInput.getCouples() != null && !cageInput.getCouples().isEmpty()) {
				for(UUID coupleId : cageInput.getCouples()) {
					Optional<Nest> nest = this.nestService.getNestByCoupleId(coupleId, userProfileId);
					
					if(nest.isPresent()) {
						if(cageInput.getNests() != null && !cageInput.getNests().contains(nest.get().getId())) {
							cageInput.getNests().add(nest.get().getId());
						}else {
							nestIds.add(nest.get().getId());
							cageInput.setNests(nestIds);
						}
					}
				}
				
			}
			
			
		}
				
		return cageInput;
	}
	
	private ResultSetExtractor<List<Cage>> cageResultExtractor(UUID userProfileId) {
		ResultSetExtractor<List<Cage>> resultExtractor = (resultSet) -> {
			List<Cage> cages = new ArrayList<Cage>();
			while(resultSet.next()){
				Cage cage = this.resultSetToCage(resultSet, userProfileId);
				
				cages.add(cage);
			};
			
			return cages;
		};
		
		return resultExtractor;
		
	}

	private Cage resultSetToCage(ResultSet resultSet, UUID userProfileId) {
		Cage cage = null;
		
		try {
			UUID id = (UUID) resultSet.getObject("id");
			String displayName = resultSet.getString("display_name");
			String description = resultSet.getString("description");
			Array nests = resultSet.getArray("nests");
			Array birds = resultSet.getArray("birds");
			Array couples = resultSet.getArray("couples");
			
			ArrayList<Nest> nestList = new ArrayList<Nest>();
			if(nests != null) {
				UUID[] nestArray = (UUID[]) nests.getArray();
				for (int i = 0; i < nestArray.length; i++) {
					Optional<Nest> nest = nestService.getNestById(nestArray[i], userProfileId);
					if(nest.isPresent()) {
						nestList.add(nest.get());
					}
				}
			}
			
			ArrayList<BirdAllInfo> birdList = new ArrayList<BirdAllInfo>();
			if(birds != null) {
				UUID[] birdArray = (UUID[]) birds.getArray();
				for (int i = 0; i < birdArray.length; i++) {
					Optional<BirdAllInfo> bird = birdService.getBirdById(birdArray[i], userProfileId);
					if(bird.isPresent()) {
						birdList.add(bird.get());
					}
				}
			}
			
			ArrayList<Couple> coupleList = new ArrayList<Couple>();
			if(couples != null) {
				UUID[] coupleArray = (UUID[]) couples.getArray();
				for (int i = 0; i < coupleArray.length; i++) {
					Optional<Couple> couple = coupleService.getCoupleById(coupleArray[i], userProfileId);
					if(couple.isPresent()) {
						coupleList.add(couple.get());
					}
				}
			}
			
			cage = new Cage(
					id, 
					displayName,
					description,
					nestList,
					birdList,
					coupleList,
					userProfileId
				);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cage;
	}
}
