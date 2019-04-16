
package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.PositionRepository;
import domain.Position;

@Service
@Transactional
public class PositionService {

	@Autowired
	private PositionRepository	positionRepository;


	public List<Position> getPositionByProblemId(final int id) {
		return this.positionRepository.findByProblemId(id);
	}

	public void save(final Position p) {
		this.positionRepository.save(p);
	}

	public Collection<Position> findAll() {
		return positionRepository.findAll();
	}
}
