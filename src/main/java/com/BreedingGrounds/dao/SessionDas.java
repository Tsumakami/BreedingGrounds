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

import com.BreedingGrounds.model.user.Session;
import com.BreedingGrounds.service.GenericService;

@Repository("session")
public class SessionDas extends GenericService implements SessionDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public SessionDas(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public int insertSession(UUID id, Session session) {
		final String sql = "INSERT INTO session"
				+ " (id, jwt_token, creation_date, valid) "
				+ "VALUES (?, ?, ?, ?)";
		int result = 0; 
		
		try {
			session.setId(id);
			
			logger.debug("Insert {}", session.toString());
						
			Object params[] = {
					session.getId(), 
					session.getJwtToken(),
					session.getCreateDate(),
					session.isValid()
			};

			result = this.jdbcTemplate.update(sql, params);
			
			logger.info("Insert Success the {}.", session.toString());
		} catch (Exception e) {
			logger.error("Fail to insert {}, error={}", session.toString(), e);
		}
		
		return result;
	}

	@Override
	public List<Session> selectAllsessions() {
		final String sql = "SELECT * FROM session";
		final ResultSetExtractor<List<Session>> resultExtractor = sessionResultExtractor();
		
		try {
			logger.debug("Select all Sessions...");
			
			List<Session> listSessions = jdbcTemplate.query(sql, resultExtractor); 
			
			logger.debug("Select all session return {} sessions.", listSessions.size());
			
			return listSessions;
			
		} catch (Exception e) {
			logger.error("Fail to select all usersProfile, error={}", e);
		}
		return null;
	}
	
	@Transactional
	@Override
	public Optional<Session> selectSessionById(UUID id) {
		final String sql = "SELECT * FROM session WHERE id = ?::uuid";
		final ResultSetExtractor<List<Session>> resultExtrator = this.sessionResultExtractor();
		Session session = null;
		
		try {
			logger.info("Select user profile by id={}", id.toString());
			
			List<Session> sessionResults = this.jdbcTemplate
					.query(sql,resultExtrator, new Object[] {id.toString()});
			
			if(sessionResults != null && !sessionResults.isEmpty()) { 
				session = sessionResults.get(0);
				logger.debug("Select session by id={} return {}", id.toString(), session.toString());
			}
				
		} catch (Exception e) {
			logger.error("Fail to select session by id={}, error={}", id.toString(), e);;
		}
		return Optional.ofNullable(session);
	}

	@Override
	public int deleteSessionById(UUID id) {
		final String sql = "DELETE FROM session WHERE id = ?::uuid";
		int result = 0;
		
		try {
			logger.debug("Delete session by id={}.", id.toString());
			
			Object params[] = {
					id.toString()
			};
			
			result = this.jdbcTemplate.update(sql, params);
			
			if(result == 1) {
				logger.debug("Delete session with id={}, was a success.", id.toString());
			}else {
				logger.warn("Delete session by id={}, return the follow result={}", id.toString(), result);
			}
		}catch (Exception e) {
			logger.error("Fail to delete session by id={}, error={}", id.toString(), e);
		}
		
		return result;
	}

	@Override
	public int updateSessionById(UUID id, Session session) {
		final String sql = "UPDATE session "
				+ "SET (jwt_token, creation_date, valid) "
				+ "= (?, ?, ?) "
				+ "WHERE id = ?::uuid";
		int result = 0; 
		
		try {
			
			logger.debug("Update session to id={}, session={}",
					id.toString(), session.toString());
			
			Object params[] = {
					session.getJwtToken(),
					session.getCreateDate(),
					session.isValid(),
					id
			};
			
			result = this.jdbcTemplate.update(sql, params);
			
			if(result == 1) {
				logger.debug("Update session with id={}, was a success.", id.toString());
			}else {
				logger.warn("Update session by id={}, return the follow result={}", id.toString(), result);
			}
		} catch (Exception e) {
			logger.error("Fail to update session with id={}, error={}", id.toString(), e);
		}
		
		return result;
	}
	
	private ResultSetExtractor<List<Session>> sessionResultExtractor() {
		ResultSetExtractor<List<Session>> resultExtractor = (resultSet) -> {
			List<Session> sessions = new ArrayList<Session>();
			while(resultSet.next()){
				Session session = this.resultSetToSession(resultSet);
				
				sessions.add(session);
			};
			
			return sessions;
		};
		
		return resultExtractor;
	}
	
	
	public Session resultSetToSession(ResultSet resultSet) {
		Session session = null;
		
		try {
			UUID id = (UUID) resultSet.getObject("id");
			String jwtToken = resultSet.getString("jwt_token");
			Date creationDate = resultSet.getDate("creation_date");
			boolean valid = resultSet.getBoolean("valid");
			
			session = new Session(
					id, 
					jwtToken,
					creationDate,
					valid
					);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return session;
	}

}
