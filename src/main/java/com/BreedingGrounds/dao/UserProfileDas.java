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

import com.BreedingGrounds.model.user.AppUserRole;
import com.BreedingGrounds.model.user.User;
import com.BreedingGrounds.model.user.UserProfile;
import com.BreedingGrounds.service.GenericService;

@Repository("userProfile")
public class UserProfileDas extends GenericService implements UserProfileDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public UserProfileDas(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	//[ERRO] Salvando a data com 1 dia a menos
	@Override
	public int insertUserProfile(UUID id, UserProfile userProfile) {
		final String sql = "INSERT INTO user_profile"
				+ " (id, name, cpf, email, user_password, birth_date, gender, enabled) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		int result = 0; 
		try {
			userProfile.setId(id);
			
			logger.debug("Insert {}", userProfile.toString());
						
			Object params[] = {
					userProfile.getId(), 
					userProfile.getName(),
					userProfile.getCpf(),
					userProfile.getEmail(),
					userProfile.getPassword(),
					userProfile.getBirthDate(),
					userProfile.getGender(),
					true
			};

			result = this.getJdbcTemplate().update(sql, params);
			
			logger.info("Insert Success the {}.", userProfile.toString());
		} catch (Exception e) {
			logger.error("Fail to insert {}, error={}", userProfile.toString(), e);
		}
		
		return result;
	}

	@Override
	public List<User> selectAllUserProfile() {
		final String sql = "SELECT * FROM user_profile";
		final ResultSetExtractor<List<User>> resultExtractor = userResultExtractor();
		
		try {
			logger.debug("Select all Users...");
			
			List<User> listUsers = getJdbcTemplate().query(sql, resultExtractor); 
			
			logger.debug("Select all users return {} users.", listUsers.size());
			
			return listUsers;
			
		} catch (Exception e) {
			logger.error("Fail to select all usersProfile, error={}", e);
		}
		return null;
	}

	@Override
	public Optional<User> selectUserProfileById(UUID id) {
		final String sql = "SELECT * FROM user_profile WHERE id = ?::uuid";
		final ResultSetExtractor<List<User>> resultExtrator = this.userResultExtractor();
		User user = null;
		
		try {
			logger.info("Select user profile by id={}", id.toString());
			
			List<User> useResults = this.getJdbcTemplate()
					.query(sql,resultExtrator, new Object[] {id.toString()});
			
			if(!useResults.isEmpty()) { 
				user = useResults.get(0);
				logger.debug("Select user profile by id={} return {}", id.toString(), user.toString());
			}
				
		} catch (Exception e) {
			logger.error("Fail to select user profile by id={}, error={}", id.toString(), e);;
		}
		return Optional.ofNullable(user);
	}
	
	@Override
	public Optional<User> getUserProfileByEmail(String email) {
		final String sql = "SELECT * FROM user_profile WHERE email = ?";
		final ResultSetExtractor<List<User>> resultExtrator = this.userResultExtractor();
		User user = null;
		
		try {
			logger.info("Select user profile by email={}", email);
			
			List<User> userProfileresults = this.getJdbcTemplate()
					.query(sql,resultExtrator, new Object[] {email});
			
			if(!userProfileresults.isEmpty()) { 
				user = userProfileresults.get(0);
				logger.debug("Select user profile by email={} return {}", email, user.toString());
			}
				
		} catch (Exception e) {
			logger.error("Fail to select user profile by email={}, error={}", email, e);;
		}
		return Optional.ofNullable(user);
	}

	@Override
	public int deleteUserProfileById(UUID id) {
		final String sql = "DELETE FROM user_profile WHERE id = ?::uuid";
		int result = 0;
		
		try {
			logger.debug("Delete user profile by id={}.", id.toString());
			
			Object params[] = {
					id.toString()
			};
			
			result = this.getJdbcTemplate().update(sql, params);
			
			if(result == 1) {
				logger.debug("Delete user profile with id={}, was a success.", id.toString());
			}else {
				logger.warn("Delete user profile by id={}, return the follow result={}", id.toString(), result);
			}
		}catch (Exception e) {
			logger.error("Fail to delete user profile by id={}, error={}", id.toString(), e);
		}
		
		return result;
	}

	@Override
	public int updateUserProfileById(UUID id, UserProfile userProfile) {
		final String sql = "UPDATE user_profile "
				+ "SET (name, cpf, email, birth_date, gender) "
				+ "= (?, ?, ?, ?, ?) "
				+ "WHERE id = ?::uuid";
		int result = 0; 
		
		try {
			
			logger.debug("Update user profile by id={}, update={}", id.toString(), userProfile.toString());
			
			Object params[] = {
					userProfile.getName(),
					userProfile.getCpf(),
					userProfile.getEmail(),
					userProfile.getBirthDate(),
					userProfile.getGender(),
					id
			};
			
			result = this.getJdbcTemplate().update(sql, params);
			
			if(result == 1) {
				logger.debug("Update user profile with id={}, was a success.", id.toString());
			}else {
				logger.warn("Update user profile by id={}, return the follow result={}", id.toString(), result);
			}
		} catch (Exception e) {
			logger.error("Fail to update user profile by id={}, error={}", id.toString(), e);
		}
		
		return result;
	}
	
	@Override
	public int updatePasswordById(UUID id, UserProfile userProfile) {
		final String sql = "UPDATE user_profile "
				+ "SET (user_password) "
				+ "= (?) "
				+ "WHERE id = ?::uuid";
		int result = 0; 
		
		try {
			
			logger.debug("Update password to user profile id={}, password={}",
					id.toString(), userProfile.getPassword());
			
			Object params[] = {
					userProfile.getPassword(),
					id
			};
			
			result = this.getJdbcTemplate().update(sql, params);
			
			if(result == 1) {
				logger.debug("Update password to user profile with id={}, was a success.", id.toString());
			}else {
				logger.warn("Update password to user profile by id={}, return the follow result={}", id.toString(), result);
			}
		} catch (Exception e) {
			logger.error("Fail to update password in user profile with id={}, error={}", id.toString(), e);
		}
		
		return result;
	}
	
	@Override
	public int enableUserProfile(String email, boolean enabled) {
		final String sql = "UPDATE user_profile "
				+ "SET enabled = ? "
				+ "WHERE email = ?";
		int result = 0; 
		
		try {
			
			logger.debug("Update enabled to True by user profile with email={}", email);
			
			Object params[] = { enabled, email };
			
			result = this.getJdbcTemplate().update(sql, params);
			
			if(result == 1) {
				logger.debug("Update enabled to {} by user profile with email={}.", enabled, email);
			}else {
				logger.warn("Update enabled to user profile by email={}, return the follow result={}", email, result);
			}
		} catch (Exception e) {
			logger.error("Fail to update enabled in user profile with email={}, error={}", email, e);
		}
		
		return result;
	}
	
	@Override
	public int updateSessionById(UUID id, UUID sessionId) {
		final String sql = "UPDATE user_profile "
				+ "SET session_id = ? "
				+ "WHERE id = ?::uuid";
		int result = 0; 
		
		try {
			
			logger.debug("Update sessionId by user profile with idl={}", id.toString());
			
			Object params[] = {sessionId, id };
			
			result = this.getJdbcTemplate().update(sql, params);
			
			if(result == 1) {
				logger.debug("Update sessionId to {} by user profile with id={}.", sessionId, id);
			}else {
				logger.warn("Update sessionId to user profile by id={}, return the follow result={}", id, result);
			}
		} catch (Exception e) {
			logger.error("Fail to update sessionId in user profile with id={}, error={}", id, e);
		}
		
		return result;
	}
	
	private ResultSetExtractor<List<User>> userResultExtractor(){
		ResultSetExtractor<List<User>> resultExtractor = (resultSet) -> {
			List<User> users = new ArrayList<User>();
			while(resultSet.next()){
				User user = this.resultSetToUser(resultSet);
				
				users.add(user);
			};
			
			return users;
		};
		
		return resultExtractor;
	}
	
	public User resultSetToUser(ResultSet resultSet) {
		User user = null;
		
		try {
			UUID id = (UUID) resultSet.getObject("id");
			String name = resultSet.getString("name");
			String cpf = resultSet.getString("cpf");
			String email = resultSet.getString("email");
			String password = resultSet.getString("user_password");
			Date birthDate = resultSet.getDate("birth_date");
			char gender = resultSet.getString("gender").charAt(0);
			boolean enabled = resultSet.getBoolean("enabled");
			boolean locked = resultSet.getBoolean("locked");
			String appUserRole = resultSet.getString("app_user_role");
			UUID sessionId = (UUID) resultSet.getObject("session_id");
			
			user = new User(
					id, 
					name,
					cpf,
					email,
					password,
					birthDate,
					gender,
					enabled,
					locked,
					AppUserRole.valueOf(appUserRole),
					sessionId
					);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

}
