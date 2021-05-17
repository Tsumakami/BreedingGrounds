package com.BreedingGrounds.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BreedingGrounds.dao.SessionDao;
import com.BreedingGrounds.model.user.Session;

@Service
public class SessionService extends GenericService {

	private final SessionDao sessionDao;

	@Autowired
	public SessionService(SessionDao sessionDao) {
		this.sessionDao = sessionDao;
	}

	public int addSession(Session session) {
		return sessionDao.addSession(session);
	}
	
	public int updateSessionById(UUID id, Session session) {
		return sessionDao.updateSessionById(id, session);
	}
	
	public Optional<Session> selectSessionById(UUID id) {
		return sessionDao.selectSessionById(id);
	}
	
	public int deleteSession(UUID id) {
		return sessionDao.deleteSessionById(id);
	}
	
}
