
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MiscellaneousDataRepository;
import security.Authority;
import domain.Curricula;
import domain.Hacker;
import domain.MiscellaneousData;

@Service
@Transactional
public class MiscellaneousDataService {

	@Autowired
	private MiscellaneousDataRepository	miscellaneousDataRepository;

	@Autowired
	private HackerService				hackerService;

	@Autowired
	private CurriculaService			curriculaService;


	public List<MiscellaneousData> findByCurriculaId(final int curriculaId) {
		this.checkConditions();
		return this.miscellaneousDataRepository.findMiscellaneousDataByCurriculaId(curriculaId);
	}

	private void checkConditions() {
		final Hacker h = this.hackerService.findOnePrincipal();
		final Authority a = new Authority();
		a.setAuthority(Authority.HACKER);
		Assert.isTrue(h.getUserAccount().getAuthorities().contains(a));
		Assert.isTrue(!h.getIsBanned());
	}

	public MiscellaneousData create(final int curriculaId) {
		final Curricula c = this.curriculaService.findOne(curriculaId);
		Assert.isTrue(this.curriculaService.getCurriculasFromHacker().contains(c));

		final MiscellaneousData res = new MiscellaneousData();
		res.setCurricula(c);
		res.setId(0);
		return res;
	}

	public MiscellaneousData findOne(final int id) {
		this.checkConditions();
		final Hacker h = this.hackerService.findOnePrincipal();
		final MiscellaneousData m = this.miscellaneousDataRepository.findOne(id);
		Assert.isTrue(h == m.getCurricula().getHacker());
		return m;
	}

	public void save(final MiscellaneousData e) {
		this.checkConditions();
		Assert.isTrue(e.getCurricula().getHacker().getId() == this.hackerService.findOnePrincipal().getId());

		this.miscellaneousDataRepository.save(e);
	}

	public void delete(final MiscellaneousData p) {
		this.checkConditions();
		Assert.isTrue(this.miscellaneousDataRepository.findOne(p.getId()).getCurricula().getHacker() == this.hackerService.findOnePrincipal());

		this.miscellaneousDataRepository.delete(p);
	}

}
