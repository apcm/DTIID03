
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.HackerRepository;
import domain.Hacker;

@Service
@Transactional
public class HackerService {

	@Autowired
	private HackerRepository	hr;


	public Hacker getHackerByUserAccount(final int id) {
		return this.hr.findByUserAccountId(id);

	}
}
