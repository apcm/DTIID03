
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.PositionRepository;

@Service
@Transactional
public class PositionService {

	@Autowired(required = true)
	PositionRepository	positionRepository;


	public Collection<String> searchPositions(final String keyword) {
		return this.positionRepository.searchPositions(keyword);
	}
}
