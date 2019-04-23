
package services;

import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

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
	private Validator					validator;

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
		final Curricula c = this.curriculaRepository.findOne(curriculaId);
		this.checkConditions();
		Assert.isTrue(this.getCurriculasFromHacker().contains(c));
		return c;
	}

	public List<Curricula> getCurriculasFromHacker() {
		this.checkConditions();
		final Hacker h = this.hackerService.findOnePrincipal();
		final List<Curricula> lc = this.curriculaRepository.findCurriculaByHackerId(h.getId());
		return lc;
	}

	public List<Curricula> getCurriculasFromHackerNotCopies() {
		this.checkConditions();
		final Hacker h = this.hackerService.findOnePrincipal();
		final List<Curricula> lc = this.curriculaRepository.findCurriculaByHackerIdNotCopies(h.getId());
		return lc;
	}

	public Curricula create() {
		final Curricula res = new Curricula();
		final Hacker h = this.hackerService.findOnePrincipal();
		res.setId(0);
		res.setHacker(h);
		res.setIsCopy(false);

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
			Curricula copy = this.create();

			copy.setHacker(c.getHacker());
			copy.setApplication(c.getApplication());
			final String title = c.getName();
			copy.setName(title + " copy");
			copy = this.curriculaRepository.save(copy);
			//Falta guardar una copia de cada apartado y asociarlo a la copia
			final List<EducationData> ed = this.educationDataService.findByCurriculaId(c.getId());
			final List<PersonalData> pd = this.personalDataService.findByCurriculaId(c.getId());
			final List<PositionData> pd2 = this.positionDataService.findByCurriculaId(c.getId());
			final List<MiscellaneousData> md = this.miscellaneousDataService.findByCurriculaId(c.getId());
			for (final EducationData ed1 : ed) {
				final EducationData newEd = this.educationDataService.create(copy.getId());
				newEd.setDegree(ed1.getDegree());
				newEd.setEndMoment(ed1.getEndMoment());
				newEd.setInstitution(ed1.getInstitution());
				newEd.setMark(ed1.getMark());
				newEd.setStartMoment(ed1.getStartMoment());
				this.educationDataService.save(newEd);
			}
			for (final PersonalData pd1 : pd) {
				final PersonalData newPd = this.personalDataService.create(copy.getId());
				newPd.setFullName(pd1.getFullName());
				newPd.setGitProfile(pd1.getGitProfile());
				newPd.setLinkedInProfile(pd1.getLinkedInProfile());
				newPd.setPhoneNumber(pd1.getPhoneNumber());
				newPd.setStatement(pd1.getStatement());
				this.personalDataService.save(newPd);
			}
			for (final PositionData posi : pd2) {
				final PositionData newPosi = this.positionDataService.create(copy.getId());
				newPosi.setDescription(posi.getDescription());
				newPosi.setEndMoment(posi.getEndMoment());
				newPosi.setStartMoment(posi.getStartMoment());
				newPosi.setTitle(posi.getTitle());

				this.positionDataService.save(newPosi);
			}
			for (final MiscellaneousData md1 : md) {
				final MiscellaneousData newMd = this.miscellaneousDataService.create(copy.getId());
				newMd.setAttachments(md1.getAttachments());
				newMd.setFreeText(md1.getFreeText());

				this.miscellaneousDataService.save(newMd);
			}
			c.setApplication(null);
			copy.setIsCopy(true);
			this.curriculaRepository.save(copy);
		}
		this.curriculaRepository.save(c);

	}

	public void delete(final Curricula c) {
		this.checkConditions();
		Assert.isTrue(this.getCurriculasFromHacker().contains(c));

		if (c.getIsCopy())
			Assert.isTrue(c.getApplication() == null);

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

	public Curricula reconstruct(final Curricula c, final BindingResult binding) {
		Curricula res;
		if (c.getId() == 0)
			res = c;
		else {
			res = this.findOne(c.getId());
			res.setName(c.getName());
		}

		this.validator.validate(res, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return res;
	}

	public Curricula reconstructApplicationC(final Curricula c, final BindingResult binding) {
		Curricula res;
		res = this.findOne(c.getId());
		res.setApplication(c.getApplication());

		this.validator.validate(res, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return res;

	}

	public void flush() {
		this.curriculaRepository.flush();

	}
}
