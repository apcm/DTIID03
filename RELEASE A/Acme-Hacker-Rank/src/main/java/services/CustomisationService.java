
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomisationRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Customisation;

@Transactional
@Service
public class CustomisationService {

	@Autowired
	private CustomisationRepository	customisationRepository;


	private boolean checkAdmin() {
		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		final UserAccount user = LoginService.getPrincipal();
		return user.getAuthorities().contains(a);
	}

	public Customisation create() {
		Assert.isTrue(this.checkAdmin());
		return new Customisation();
	}

	public Collection<Customisation> findAll() {
		return this.customisationRepository.findAll();
	}
	public Customisation findOne(final int customisationId) {
		return this.customisationRepository.findOne(customisationId);
	}

	public Customisation save(final Customisation customisation) {
		Assert.isTrue(this.checkAdmin());
		return this.customisationRepository.save(customisation);
	}

	public void delete(final Customisation customisation) {
		Assert.isTrue(this.checkAdmin());
		this.customisationRepository.delete(customisation);
	}

	public Customisation getCustomisation() {
		final List<Customisation> x = new ArrayList<Customisation>(this.findAll());
		Customisation res;

		res = x.get(0);

		return res;

	}
}
