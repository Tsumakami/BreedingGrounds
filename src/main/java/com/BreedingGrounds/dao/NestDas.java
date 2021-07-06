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
import org.springframework.transaction.annotation.Transactional;

import com.BreedingGrounds.model.couple.Couple;
import com.BreedingGrounds.model.nest.Nest;
import com.BreedingGrounds.model.nest.NestInput;
import com.BreedingGrounds.service.CoupleService;
import com.BreedingGrounds.service.GenericService;

@Repository("nest")
public class NestDas extends GenericService implements NestDao {
	private final JdbcTemplate jdbcTemplate;
	private final CoupleService coupleService;
	
	@Autowired
	public NestDas(JdbcTemplate jdbcTemplate, CoupleService coupleService) {
		this.jdbcTemplate = jdbcTemplate;
		this.coupleService = coupleService;
	}
	
	@Override
	public int createNest(UUID id, NestInput nestInput, UUID userProfileId) {
		final String sql = "INSERT INTO nest"
				+ " (id, display_name, description, couple, user_profile_id) "
				+ "VALUES (?, ?, ?, ?, ?)";
		int result = 0; 
		
		try {
						
			logger.debug("Create nest={} to userPerfil={}", nestInput.toString(), userProfileId.toString());
						
			Object params[] = {
					id, 
					nestInput.getDisplayName(),
					nestInput.getDescription(),
					nestInput.getCoupleId(),
					userProfileId
			};

			result = this.jdbcTemplate.update(sql, params);
			
			logger.info("Insert nest with success. Nest={}.", nestInput.toString());
		} catch (Exception e) {
			logger.error("Fail to insert nest={}, error={}", nestInput.toString(), e);
		}
		
		return result;
	}

	@Override
	public List<Nest> selectAllNests(UUID userProfileId) {
		final String sql = "SELECT * FROM nest where user_profile_id = ?::uuid ";
		final ResultSetExtractor<List<Nest>> resultExtractor = nestResultExtractor(userProfileId);
		
		try {
			logger.debug("Select all nests...");
			
			List<Nest> listNests = jdbcTemplate.query(sql, resultExtractor, new Object[] { userProfileId.toString() }); 
			
			logger.debug("Select all nests return {} couples.", listNests.size());
			
			return listNests;
			
		} catch (Exception e) {
			logger.error("Fail to select all usersProfile, error={}", e);
		}
		return null;
	}

	@Transactional
	@Override
	public Optional<Nest> selectNestById(UUID id, UUID userProfileId) {
		final String sql = "SELECT * FROM nest WHERE id = ?::uuid and user_profile_id = ?::uuid";
		final ResultSetExtractor<List<Nest>> resultExtractor = nestResultExtractor(userProfileId);
		Nest nest = null;
		
		try {
			logger.debug("Select nest by Id={}",id.toString());
			
			List<Nest> listNests = jdbcTemplate
					.query(sql, resultExtractor, new Object[] {id.toString(), userProfileId.toString()}); 
			
			if(!listNests.isEmpty()) { 
				nest = listNests.get(0);
				logger.debug("Select nest by id={}, return {}.", id, nest.toString());
			}
			
		} catch (Exception e) {
			logger.error("Fail to select nest by id={}, error={}", id.toString(), e);
		}
		
		return Optional.ofNullable(nest);
	}

	@Transactional
	@Override
	public Optional<Nest> selectNestByCoupleId(UUID coupleId, UUID userProfileId) {
		final String sql = "SELECT * FROM nest WHERE couple = ?::uuid and user_profile_id = ?::uuid";
		final ResultSetExtractor<List<Nest>> resultExtractor = nestResultExtractor(userProfileId);
		Nest nest = null;
		
		try {
			logger.debug("Select nest by couple Id={}",coupleId.toString());
			
			List<Nest> listNests = jdbcTemplate
					.query(sql, resultExtractor, new Object[] {coupleId.toString(), userProfileId.toString()}); 
			
			if(!listNests.isEmpty()) { 
				nest = listNests.get(0);
				logger.debug("Select nest by couple id={}, return {}.", coupleId, nest.toString());
			}
			
		} catch (Exception e) {
			logger.error("Fail to select nest by couple id={}, error={}", coupleId.toString(), e);
		}
		
		return Optional.ofNullable(nest);
	}
	
	@Override
	public int deleteNestById(UUID id, UUID userProfileId) {
		final String sql = "DELETE FROM nest WHERE id = ?::uuid and user_profile_id = ?::uuid";
		int result = 0;
		
		try {
			logger.debug("Delete nest by id={}.", id.toString());
			
			Object params[] = {
					id.toString(),
					userProfileId.toString()
			};
			
			result = this.jdbcTemplate.update(sql, params);
			
			if(result == 1) {
				logger.debug("Delete nest with id={}, success.", id.toString());
			}else {
				logger.warn("Delete nest by id={}, return the folow result={}", id.toString(), result);
			}
		}catch (Exception e) {
			logger.error("Fail to delete nest by id={}, error={}", id.toString(), e);
		}
		
		return result;
	}

	@Override
	public int updateNestById(UUID id, NestInput nestInput, UUID userProfileId) {
		final String sql = "UPDATE nest"
				+ " SET (display_name, description, couple)"
				+ " = (?, ?, ?)"
				+ " WHERE id = ?::uuid and user_profile_id = ?::uuid";
		
		int result = 0; 
		
		try {
			logger.debug("Update nest={}", nestInput.toString());
			
			Object params[] = {
				nestInput.getDisplayName(),
				nestInput.getDescription(),
				nestInput.getCoupleId(),
				id.toString(),	
				userProfileId.toString()
			};

			result = this.jdbcTemplate.update(sql, params);
			
			logger.info("Update nest with success. Nest={}.", nestInput.toString());
			
		} catch (Exception e) {
			logger.error("Fail to update nest={}, error=", nestInput.toString(), e);
		}
		
		return result;
	}

	private ResultSetExtractor<List<Nest>> nestResultExtractor(UUID userProfileId) {
		ResultSetExtractor<List<Nest>> resultExtractor = (resultSet) -> {
			List<Nest> nests = new ArrayList<Nest>();
			while(resultSet.next()){
				Nest nest = this.resultSetToNest(resultSet, userProfileId);
				
				nests.add(nest);
			};
			
			return nests;
		};
		
		return resultExtractor;
	}

	private Nest resultSetToNest(ResultSet resultSet, UUID userProfileId) {
		Nest nest = null;
		
		try {
			UUID id = (UUID) resultSet.getObject("id");
			String displayName = resultSet.getString("display_name");
			String description = resultSet.getString("description");
			UUID coupleId = (UUID) resultSet.getObject("couple");
			
			Optional<Couple> couple = Optional.ofNullable(null);
			
			if(coupleId != null) {
				couple = coupleService.getCoupleById(coupleId, userProfileId);
			}
			
			nest = new Nest(
					id, 
					displayName,
					description,
					couple.orElse(null),
					userProfileId
				);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return nest;
	}
	
}
