package com.BreedingGrounds.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.BreedingGrounds.model.couple.Couple;
import com.BreedingGrounds.model.couple.CoupleInput;

public class CoupleDas implements CoupleDao {

	@Override
	public int createCouple(UUID id, CoupleInput coupleInput, UUID userProfileId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Couple> selectAllCouples(UUID userProfileId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Couple> selectCoupleById(UUID id, UUID userProfileId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteCoupleById(UUID id, UUID userProfileId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateCoupleById(UUID id, CoupleInput coupleInput, UUID userProfileId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
