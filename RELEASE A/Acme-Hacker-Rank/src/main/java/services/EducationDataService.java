
package services;

import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.EducationDataRepository;
import security.Authority;
import domain.Curricula;
import domain.EducationData;
import domain.Hacker;

@Service
@Transactional
public class EducationDataService {

	@Autowired
	private Validator				validator;

	@Autowired
	private EducationDataRepository	educationDataRepository;

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private CurriculaService		curriculaService;


	public List<EducationData> findByCurriculaId(final int curriculaId) {
		this.checkConditions();
		final List<Curricula> lc = this.curriculaService.getCurriculasFromHacker();
		final List<EducationData> res = this.educationDataRepository.findEducationDataByCurriculaId(curriculaId);
		for (final EducationData ed : res)
			Assert.isTrue(lc.contains(ed.getCurricula()));
		return res;
	}

	private void checkConditions() {
		final Hacker h = this.hackerService.findOnePrincipal();
		final Authority a = new Authority();
		a.setAuthority(Authority.HACKER);
		Assert.isTrue(h.getUserAccount().getAuthorities().contains(a));
		Assert.isTrue(!h.getIsBanned());
	}

	public EducationData create(final int curriculaId) {
		final Curricula c = this.curriculaService.findOne(curriculaId);
		Assert.isTrue(this.curriculaService.getCurriculasFromHacker().contains(c));
		Assert.isTrue(this.curriculaService.findOne(curriculaId).getIsCopy() == false);

		final EducationData res = new EducationData();
		res.setCurricula(c);
		res.setId(0);
		return res;
	}

	public EducationData findOne(final int id) {
		this.checkConditions();
		final Hacker h = this.hackerService.findOnePrincipal();
		final EducationData p = this.educationDataRepository.findOne(id);
		Assert.isTrue(h == p.getCurricula().getHacker());
		return p;
	}

	public void save(final EducationData e) {
		this.checkConditions();
		Assert.isTrue(e.getCurricula().getHacker().getId() == this.hackerService.findOnePrincipal().getId());

		this.educationDataRepository.save(e);
	}

	public void delete(final EducationData p) {
		this.checkConditions();
		Assert.isTrue(!p.getCurricula().getIsCopy());
		Assert.isTrue(this.educationDataRepository.findOne(p.getId()).getCurricula().getHacker() == this.hackerService.findOnePrincipal());

		this.educationDataRepository.delete(p);
	}

	public EducationData reconstruct(final EducationData ed, final BindingResult binding) {
		EducationData res;
		if (ed.getId() == 0)
			res = ed;
		else {
			res = this.findOne(ed.getId());
			res.setDegree(ed.getDegree());
			res.setEndMoment(ed.getEndMoment());
			res.setInstitution(ed.getInstitution());
			res.setMark(ed.getMark());
			res.setStartMoment(ed.getStartMoment());

		}
		this.validator.validate(res, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return res;
	}

	public void flush() {
		this.educationDataRepository.flush();

	}

}
