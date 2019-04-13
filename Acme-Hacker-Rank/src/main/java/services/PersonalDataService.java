
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PersonalDataRepository;
import security.Authority;
import domain.Curricula;
import domain.Hacker;
import domain.PersonalData;

@Service
@Transactional
public class PersonalDataService {

	@Autowired
	private PersonalDataRepository	personalDataRepository;

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private CurriculaService		curriculaService;


	public List<PersonalData> findByCurriculaId(final int curriculaId) {
		this.checkConditions();
		return this.personalDataRepository.findPersonalDataByCurriculaId(curriculaId);
	}

	private void checkConditions() {
		final Hacker h = this.hackerService.findOnePrincipal();
		final Authority a = new Authority();
		a.setAuthority(Authority.HACKER);
		Assert.isTrue(h.getUserAccount().getAuthorities().contains(a));
		Assert.isTrue(!h.getIsBanned());
	}

	public PersonalData create(final int curriculaId) {
		final Curricula c = this.curriculaService.findOne(curriculaId);
		Assert.isTrue(this.curriculaService.getCurriculasFromHacker().contains(c));

		final PersonalData res = new PersonalData();
		res.setCurricula(c);
		res.setId(0);
		return res;
	}

	public PersonalData findOne(final int id) {
		this.checkConditions();
		final Hacker h = this.hackerService.findOnePrincipal();
		final PersonalData p = this.personalDataRepository.findOne(id);
		Assert.isTrue(h == p.getCurricula().getHacker());
		return p;
	}

	public void save(final PersonalData e) {
		this.checkConditions();
		Assert.isTrue(e.getCurricula().getHacker().getId() == this.hackerService.findOnePrincipal().getId());

		this.personalDataRepository.save(e);
	}

	public void delete(final PersonalData p) {
		this.checkConditions();
		Assert.isTrue(this.personalDataRepository.findOne(p.getId()).getCurricula().getHacker() == this.hackerService.findOnePrincipal());

		this.personalDataRepository.delete(p);
	}

}
