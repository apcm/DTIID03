
package services;

import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PositionDataRepository;
import security.Authority;
import domain.Curricula;
import domain.Hacker;
import domain.PositionData;

@Service
@Transactional
public class PositionDataService {

	@Autowired
	private Validator				validator;

	@Autowired
	private PositionDataRepository	positionDataRepository;

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private CurriculaService		curriculaService;


	public List<PositionData> findByCurriculaId(final int curriculaId) {
		this.checkConditions();
		final List<Curricula> lc = this.curriculaService.getCurriculasFromHacker();
		final List<PositionData> res = this.positionDataRepository.findPositionDataByCurriculaId(curriculaId);
		for (final PositionData ed : res)
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

	public PositionData create(final int curriculaId) {
		final Curricula c = this.curriculaService.findOne(curriculaId);
		Assert.isTrue(this.curriculaService.getCurriculasFromHacker().contains(c));
		Assert.isTrue(this.curriculaService.findOne(curriculaId).getIsCopy() == false);

		final PositionData res = new PositionData();
		res.setCurricula(c);
		res.setId(0);
		return res;
	}

	public PositionData findOne(final int id) {
		this.checkConditions();
		final Hacker h = this.hackerService.findOnePrincipal();
		final PositionData p = this.positionDataRepository.findOne(id);
		Assert.isTrue(h == p.getCurricula().getHacker());
		return p;
	}

	public void save(final PositionData p) {
		this.checkConditions();
		Assert.isTrue(p.getCurricula().getHacker().getId() == this.hackerService.findOnePrincipal().getId());

		this.positionDataRepository.save(p);
	}

	public void delete(final PositionData p) {
		this.checkConditions();
		Assert.isTrue(!p.getCurricula().getIsCopy());
		Assert.isTrue(this.positionDataRepository.findOne(p.getId()).getCurricula().getHacker() == this.hackerService.findOnePrincipal());

		this.positionDataRepository.delete(p);
	}

	public PositionData reconstruct(final PositionData pd, final BindingResult binding) {
		PositionData res;
		if (pd.getId() == 0)
			res = pd;
		else {
			res = this.findOne(pd.getId());
			res.setDescription(pd.getDescription());
			res.setEndMoment(pd.getEndMoment());
			res.setStartMoment(pd.getStartMoment());
			res.setTitle(pd.getTitle());

		}
		this.validator.validate(res, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return res;
	}

	public void flush() {
		this.positionDataRepository.flush();
	}
}
