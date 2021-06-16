package com.BreedingGrounds.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.BreedingGrounds.model.user.Session;

@Repository("session")
public interface SessionDao {
	int insertSession(UUID id, Session session);
	
	default int addSession(Session session) {
		UUID id = UUID.randomUUID();
		return insertSession(id, session);
	}
	
	List<Session> selectAllsessions();
	
	Optional<Session> selectSessionById(UUID id);
	
	int deleteSessionById(UUID  id);
	
	int updateSessionById(UUID id, Session session);
}
