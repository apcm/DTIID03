
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.FinderRepository;
import domain.Finder;

@Transactional
@Service
public class FinderService {

	@Autowired
	FinderRepository	finderRepository;


	public Finder save(final Finder f) {
		return this.finderRepository.save(f);
	}
}
