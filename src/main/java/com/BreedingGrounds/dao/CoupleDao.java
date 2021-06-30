package com.BreedingGrounds.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.BreedingGrounds.model.couple.Couple;
import com.BreedingGrounds.model.couple.CoupleInput;
import com.BreedingGrounds.model.couple.Posture;
import com.BreedingGrounds.model.couple.PostureInput;

@Repository("couple")
public interface CoupleDao {
	int createCouple(UUID id, CoupleInput coupleInput, UUID userProfileId);
	
	default int insertCouple(CoupleInput coupleInput, UUID userProfileId) {
		UUID id = UUID.randomUUID();
		return this.createCouple(id, coupleInput, userProfileId);
	}
	
	List<Couple> selectAllCouples(UUID userProfileId);
	
	Optional<Couple> selectCoupleById(UUID id, UUID userProfileId);
	
	int deleteCoupleById(UUID  id, UUID userProfileId);
	
	int updateCoupleById(UUID id, CoupleInput coupleInput, UUID userProfileId);
	
}
