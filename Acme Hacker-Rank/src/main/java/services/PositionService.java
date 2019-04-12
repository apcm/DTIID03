
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.PositionRepository;
import domain.Application;
import domain.Hacker;
import domain.Position;

@Service
@Transactional
public class PositionService {

	@Autowired
	private PositionRepository	pr;


	public Position findOne(final int id) {
		return this.pr.findOne(id);

	}

	public List<Application> getApplicationsByHacker(final Hacker p) {
		return this.pr.getApplicationsByHacker(p);
	}

}
