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

import com.BreedingGrounds.model.Specie;
import com.BreedingGrounds.service.GenericService;

@Repository("specie")
public class SpecieDas extends GenericService implements SpecieDao {
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public SpecieDas(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int insertSpecie(UUID id, Specie specie, UUID userProfileId) {
		final String sql = "INSERT INTO specie"
				+ " (id, name, breed, incubation_period, user_profile_id) "
				+ "VALUES (?, ?, ?, ?, ?)";
		int result = 0; 
		try {
			specie.setId(id);
			
			logger.debug("Insert {}", specie.toString());
						
			Connection connection = this.getJdbcTemplate().getDataSource().getConnection();
			Array breeds = connection.createArrayOf("varchar", specie.getBreed().toArray());
			
			Object params[] = {
					specie.getId(), 
					specie.getName(), 
					breeds, 
					specie.getIncubationPeriod(),
					userProfileId
			};

			result = this.getJdbcTemplate().update(sql, params);
			
			logger.info("Insert Success the {}.", specie.toString());
			
		} catch (SQLException e) {
			logger.error("Fail to insert {}, error={}", specie.toString(), e);
		}
		
		return result;
	}
	
	@Override
	public List<Specie> selectAllSpecie(UUID userProfileId) {
		final String sql = "SELECT * FROM specie where user_profile_id = ?::uuid";
		final ResultSetExtractor<List<Specie>> resultExtractor = specieResultExtractor();
		
		try {
			logger.debug("Select all species...");
			
			List<Specie> listSpecies = getJdbcTemplate().query(sql, resultExtractor, new Object[] { userProfileId.toString() }); 
			
			logger.debug("Select all species return {} species.", listSpecies.size());
			
			return listSpecies;
			
		} catch (Exception e) {
			logger.error("Fail to select all species, error={}", e);
		}
		return null;
	}
	
	@Override
	public Optional<Specie> selectSpecieById(UUID id, UUID userProfileId) {
		final String sql = "SELECT * FROM specie WHERE id = ?::uuid and user_profile_id = ?::uuid";
		final ResultSetExtractor<List<Specie>> resultExtrator = specieResultExtractor();
		Specie specie = null;
		
		try {
			logger.info("Select specie by id={}", id.toString());
			
			List<Specie> specieresults = this.getJdbcTemplate()
					.query(sql,resultExtrator, new Object[] {id.toString(), userProfileId.toString()});
			
			if(!specieresults.isEmpty()) { 
				specie = specieresults.get(0);
				logger.debug("Select by id={} return {}", id.toString(), specie.toString());
			}
				
		} catch (Exception e) {
			logger.error("Fail to select specie by id={}, error={}", id.toString(), e);;
		}
		return Optional.ofNullable(specie);
	}

	@Override
	public int deleteSpecieById(UUID id, UUID userProfileId) {
		final String sql = "DELETE FROM specie WHERE id = ?::uuid and user_profile_id = ?::uuid";
		int result = 0;
		
		try {
			logger.debug("Delete specie by id={}.", id.toString());
			
			Object params[] = {
					id.toString(),
					userProfileId.toString()
			};
			
			result = this.getJdbcTemplate().update(sql, params);
			
			if(result == 1) {
				logger.debug("Delete specie with id={}, was a success.", id.toString());
			}else {
				logger.warn("Delete specie by id={}, return the folow result={}", id.toString(), result);
			}
		}catch (Exception e) {
			logger.error("Fail to delete specie by id={}, error={}", id.toString(), e);
		}
		
		return result;
	}

	@Override
	public int updateSpecieById(UUID id, Specie specie, UUID userProfileId) {
		final String sql = "UPDATE specie "
				+ "SET (name, breed, incubation_period) "
				+ "= (?, ?, ?) "
				+ "WHERE id = ?::uuid and user_profile_id = ?::uuid";
		int result = 0; 
		
		try {
			
			logger.debug("Update specie by id={}, update={}", id.toString(), specie.toString());
			
			Connection connection = this.getJdbcTemplate().getDataSource().getConnection();
			Array breeds = connection.createArrayOf("varchar", specie.getBreed().toArray());
			
			Object params[] = {
					specie.getName(), 
					breeds, 
					specie.getIncubationPeriod(),
					id.toString(),
					userProfileId.toString()
			};
			
			result = this.getJdbcTemplate().update(sql, params);
			
			if(result == 1) {
				logger.debug("Update specie with id={}, was a success.", id.toString());
			}else {
				logger.warn("Update specie by id={}, return the follow result={}", id.toString(), result);
			}
		} catch (SQLException e) {
			logger.error("Fail to update specie by id={}, error={}", id.toString(), e);
		}
		
		return result;
	}
	
	private ResultSetExtractor<List<Specie>> specieResultExtractor(){
		ResultSetExtractor<List<Specie>> resultExtractor = (resultSet) -> {
			List<Specie> species = new ArrayList<Specie>();
			while(resultSet.next()){
				Specie specie = this.resultSetToSpecie(resultSet);
				
				species.add(specie);
			};
			
			return species;
		};
		
		return resultExtractor;
	}
	
	public Specie resultSetToSpecie(ResultSet resultSet) {
		Specie specie = null;
		
		try {
			UUID id = (UUID) resultSet.getObject("id");
			String name = resultSet.getString("name");
			Array breeds = resultSet.getArray("breed");
			int incubationPeriod  = resultSet.getInt("incubation_period");
			ArrayList<String> breedsList = new ArrayList<String>();
			UUID userProfileId = (UUID) resultSet.getObject("user_profile_id");
			
			if(breeds != null) {
				String[] breedsArray = (String[]) breeds.getArray();
				for (int i = 0; i < breedsArray.length; i++) {
					breedsList.add(breedsArray[i]);
				}
			}

			specie = new Specie(id, name, breedsList ,incubationPeriod, userProfileId);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return specie;
	}
	
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

}
