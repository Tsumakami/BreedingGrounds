package com.BreedingGrounds.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.BreedingGrounds.model.user.Session;

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
