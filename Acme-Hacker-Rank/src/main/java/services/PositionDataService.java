
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PositionDataRepository;
import security.Authority;
import domain.Curricula;
import domain.Hacker;
import domain.PositionData;

@Service
@Transactional
public class PositionDataService {

	@Autowired
	private PositionDataRepository	positionDataRepository;

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private CurriculaService		curriculaService;


	public List<PositionData> findByCurriculaId(final int curriculaId) {
		this.checkConditions();
		return this.positionDataRepository.findPositionDataByCurriculaId(curriculaId);
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
		Assert.isTrue(this.positionDataRepository.findOne(p.getId()).getCurricula().getHacker() == this.hackerService.findOnePrincipal());

		this.positionDataRepository.delete(p);
	}
}
