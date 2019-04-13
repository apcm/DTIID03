
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CurriculaRepository;
import security.Authority;
import domain.Curricula;
import domain.EducationData;
import domain.Hacker;
import domain.MiscellaneousData;
import domain.PersonalData;
import domain.PositionData;

@Service
@Transactional
public class CurriculaService {

	@Autowired
	private CurriculaRepository			curriculaRepository;

	@Autowired
	private HackerService				hackerService;

	@Autowired
	private PersonalDataService			personalDataService;

	@Autowired
	private EducationDataService		educationDataService;

	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;

	@Autowired
	private PositionDataService			positionDataService;


	public Curricula findOne(final int curriculaId) {
		return this.curriculaRepository.findOne(curriculaId);
	}

	public List<Curricula> getCurriculasFromHacker() {
		this.checkConditions();
		final Hacker h = this.hackerService.findOnePrincipal();
		final List<Curricula> lc = this.curriculaRepository.findCurriculaByHackerId(h.getId());
		return lc;
	}

	public Curricula create() {
		final Curricula res = new Curricula();
		final Hacker h = this.hackerService.findOnePrincipal();
		res.setId(0);
		res.setHacker(h);

		return res;
	}

	private void checkConditions() {
		final Hacker h = this.hackerService.findOnePrincipal();
		final Authority a = new Authority();
		a.setAuthority(Authority.HACKER);
		Assert.isTrue(h.getUserAccount().getAuthorities().contains(a));
		Assert.isTrue(!h.getIsBanned());
	}

	public void save(final Curricula c) {
		this.checkConditions();
		Assert.isTrue(c.getHacker().getId() == this.hackerService.findOnePrincipal().getId());

		if (c.getApplication() != null) {
			Curricula copy = new Curricula();
			copy = c;
			final String title = copy.getName();
			c.setName(title + " copy");
			this.save(copy);
			//Falta guardar una copia de cada apartado y asociarlo a la copia
			c.setApplication(null);
		}
		this.curriculaRepository.save(c);
	}

	public void delete(final Curricula c) {
		this.checkConditions();
		Assert.isTrue(this.getCurriculasFromHacker().contains(c));

		final List<EducationData> l1 = this.educationDataService.findByCurriculaId(c.getId());
		for (final EducationData ed : l1)
			this.educationDataService.delete(ed);

		final List<PersonalData> l2 = this.personalDataService.findByCurriculaId(c.getId());
		for (final PersonalData pd : l2)
			this.personalDataService.delete(pd);

		final List<PositionData> l3 = this.positionDataService.findByCurriculaId(c.getId());
		for (final PositionData pd : l3)
			this.positionDataService.delete(pd);

		final List<MiscellaneousData> l4 = this.miscellaneousDataService.findByCurriculaId(c.getId());
		for (final MiscellaneousData md : l4)
			this.miscellaneousDataService.delete(md);

		this.curriculaRepository.delete(c);

	}
}
