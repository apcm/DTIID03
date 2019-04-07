package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.PositionRepository;
import domain.Position;

@Service
@Transactional
public class PositionService {
	
	@Autowired
	private PositionRepository positionRepository;

	
	public Collection<Position> findAll() {
		return positionRepository.findAll();
	}
	
	public Collection<Position> findPositionByKeyword(String keyword) {
		return positionRepository.findPositionByKeyword("%" + keyword + "%");
	}
}
