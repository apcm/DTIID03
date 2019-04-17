
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PositionRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Finder;
import domain.Position;

@Service
@Transactional
public class PositionService {

	@Autowired
	PositionRepository	positionRepository;


	public List<Position> getPositionByProblemId(final int id) {
		return this.positionRepository.findByProblemId(id);
	}
	private boolean checkHacker() {
		final Authority a = new Authority();
		a.setAuthority(Authority.HACKER);
		final UserAccount user = LoginService.getPrincipal();
		return user.getAuthorities().contains(a);
	}
	public void save(final Position p) {
		this.positionRepository.save(p);
	}
	public Collection<String> searchPositions(final String keyword) {
		return this.positionRepository.searchPositions(keyword);
	}

	public Collection<Position> findAll() {
		return this.positionRepository.findAll();
	}

	public Collection<Position> finderKeyword(final String keyword) {
		Assert.isTrue(this.checkHacker());
		return this.positionRepository.finderKeyword(keyword);
	}

	public Collection<Position> finderDeadline(final Date deadline) {
		Assert.isTrue(this.checkHacker());
		return this.positionRepository.finderDeadline(deadline);
	}

	public Collection<Position> finderMaxDeadline(final Date maxDeadline) {
		Assert.isTrue(this.checkHacker());
		return this.positionRepository.finderMaxDeadline(maxDeadline);
	}

	public Collection<Position> finderSalary(final Integer salary) {
		Assert.isTrue(this.checkHacker());
		return this.positionRepository.finderSalary(salary);
	}

	public Set<Position> finderResults(final Finder finder) {
		Assert.isTrue(this.checkHacker());
		final Set<Position> res = new HashSet<>();
		if (finder.getKeyword() != null || finder.getKeyword() != "")
			res.addAll(this.finderKeyword(finder.getKeyword()));
		else
			res.addAll(this.findAll());
		if (finder.getDeadline() != null)
			res.addAll(this.finderDeadline(finder.getDeadline()));
		else
			res.addAll(this.findAll());
		if (finder.getMaximumDeadline() != null)
			res.addAll(this.finderMaxDeadline(finder.getMaximumDeadline()));
		else
			res.addAll(this.findAll());
		if (finder.getMinimumSalary() != null)
			res.addAll(this.finderSalary(finder.getMinimumSalary()));
		else
			res.addAll(this.findAll());
		return res;
	}
}
