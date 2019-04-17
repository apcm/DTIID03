
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CompanyRepository;
import repositories.PositionRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.TickerGenerator;
import domain.Company;
import domain.Finder;
import domain.Position;
import domain.Problem;

@Service
@Transactional
public class PositionService {

	@Autowired
	private PositionRepository	positionRepository;

	@Autowired
	private CompanyRepository	companyRepository;

	@Autowired
	private ProblemService		problemService;


	public Position create() {
		final Position res = new Position();
		res.setFinalMode(false);
		res.setIsCancelled(false);
		final String ticker = TickerGenerator.tickerPosition();
		res.setTicker(ticker);
		final Collection<Problem> problems = new ArrayList<Problem>();
		res.setProblems(problems);

		return res;
	}

	public Collection<Position> findAll() {
		return this.positionRepository.findAll();
	}

	public Position findOne(final int positionId) {
		return this.positionRepository.findOne(positionId);
	}

	public Position save(final Position position) {
		Position res = new Position();
		if (this.has2Problem(position)) {
			res = this.positionRepository.save(position);
			res.setCompany(this.getThisCompany());
		}

		return res;
	}

	private boolean has2Problem(final Position position) {
		Boolean res = true;
		if (position.isFinalMode())
			if (position.getProblems().size() < 2)
				res = false;

		return res;
	}

	public void delete(final Position position) {
		this.positionRepository.delete(position);
	}

	public Collection<Position> getMyPositionList() {
		final Collection<Position> positions = this.positionsByCompany(this.findAll());
		return positions;
	}

	private Collection<Position> positionsByCompany(final Collection<Position> all) {
		final Company actual = this.getThisCompany();
		final Collection<Position> positions = new ArrayList<Position>();
		for (final Position p : all)
			if (p.getCompany().equals(actual))
				positions.add(p);
		return positions;
	}

	private Company getThisCompany() {
		Company res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.companyRepository.findByUserAccountId(userAccount.getId());
		return res;
	}

	public Position cancelP(final int positionId) {

		final Position p = this.findOne(positionId);
		p.setIsCancelled(true);
		final Position res = this.save(p);

		return res;
	}

	public Collection<Problem> getProblems(final Position position) {
		final Collection<Problem> res = new ArrayList<Problem>();
		final Collection<Problem> all = this.problemService.findAll();
		for (final Problem p : all)
			if (p.getCompany().equals(this.getThisCompany()))
				res.add(p);

		return res;
	}

	public List<Position> getPositionByProblemId(final int id) {
		return this.positionRepository.findByProblemId(id);
	}
	private boolean checkHacker() {
		final Authority a = new Authority();
		a.setAuthority(Authority.HACKER);
		final UserAccount user = LoginService.getPrincipal();
		return user.getAuthorities().contains(a);
	}

	public Collection<String> searchPositions(final String keyword) {
		return this.positionRepository.searchPositions(keyword);
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
