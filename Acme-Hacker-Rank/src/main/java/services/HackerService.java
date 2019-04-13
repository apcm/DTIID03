
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.HackerRepository;
import domain.Actor;
import domain.Hacker;

@Service
@Transactional
public class HackerService {

	@Autowired
	private HackerRepository	hackerRepository;

	@Autowired
	private ActorService		actorService;


	public Hacker findOnePrincipal() {
		final Actor a = this.actorService.findByPrincipal();
		return this.hackerRepository.findOne(a.getId());
	}
}
