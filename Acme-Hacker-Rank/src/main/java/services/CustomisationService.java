
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CustomisationRepository;
import domain.Customisation;

@Transactional
@Service
public class CustomisationService {

	@Autowired
	private CustomisationRepository	customisationRepository;


	public Customisation create() {
		return new Customisation();
	}

	public Collection<Customisation> findAll() {
		return this.customisationRepository.findAll();
	}

	public Customisation findOne(final int customisationId) {
		return this.customisationRepository.findOne(customisationId);
	}

	public Customisation save(final Customisation customisation) {
		return this.customisationRepository.save(customisation);
	}

	public void delete(final Customisation customisation) {
		this.customisationRepository.delete(customisation);
	}

	public Customisation getCustomisation() {
		final List<Customisation> x = new ArrayList<Customisation>();
		x.addAll(this.findAll());
		Customisation res;

		res = x.get(0);

		return res;

	}
}
