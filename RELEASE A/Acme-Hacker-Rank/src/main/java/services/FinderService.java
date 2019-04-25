
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FinderRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Customisation;
import domain.Finder;
import domain.Hacker;
import domain.Position;

@Service
@Transactional
public class FinderService {

	@Autowired
	FinderRepository		finderRepository;

	@Autowired
	HackerService			hackerService;

	@Autowired
	PositionService			positionService;

	@Autowired
	CustomisationService	customisationService;


	private boolean checkHacker() {
		final Authority a = new Authority();
		a.setAuthority(Authority.HACKER);
		final UserAccount user = LoginService.getPrincipal();
		return user.getAuthorities().contains(a);
	}

	public Finder getFinder() {
		Assert.isTrue(this.checkHacker());
		final Hacker a = this.hackerService.findOnePrincipal();
		return a.getFinder();
	}

	public Finder findOne(final int id) {
		return this.finderRepository.findOne(id);
	}
	//Check cached hours
	public boolean checkCache(final Finder finder) {
		Assert.isTrue(this.checkHacker());
		boolean res = false;
		final Date today = Calendar.getInstance().getTime();
		final Date moment = finder.getMoment();
		final List<Customisation> cusList = new ArrayList<>(this.customisationService.findAll());
		final Customisation cus = cusList.get(0);
		final long milisecondsDiff = Math.abs(today.getTime() - moment.getTime());
		final long hoursDiff = milisecondsDiff / 3600000;

		if (hoursDiff > cus.getFinderDuration())
			res = true;
		return res;
	}
	public Finder save(final Finder finder) {
		final Finder res = finder;
		if (finder.getId() != 0) {
			final Hacker principal = this.hackerService.findOnePrincipal();
			Assert.isTrue(principal.getFinder().getId() == finder.getId());
			final Customisation cust = this.customisationService.getCustomisation();
			final int resultsNumber = cust.getResultsNumber();

			final Set<Position> rez = this.positionService.finderResults(finder);
			List<Position> results = new ArrayList<>(rez);
			if (results.size() > resultsNumber)
				results = results.subList(0, resultsNumber);

			res.setPositions(results);
			final Calendar now = Calendar.getInstance();
			now.add(Calendar.SECOND, -1);
			res.setMoment(now.getTime());
		}
		return res;
	}
	public Finder clear(final Finder finder) {
		Assert.isTrue(this.checkHacker());
		Assert.notNull(finder);
		final Finder res = finder;
		res.setDeadline(null);
		res.setMaximumDeadline(null);
		res.setKeyword("");
		res.setMinimumSalary(null);
		final Calendar now = Calendar.getInstance();
		now.add(Calendar.SECOND, -1);
		res.setMoment(now.getTime());
		res.setPositions(new HashSet<Position>());
		return this.finderRepository.save(res);
	}


	@Autowired
	private Validator	validator;


	public Finder reconstruct(final Finder finder, final BindingResult binding) {
		Finder res;
		Assert.isTrue(this.checkHacker());
		if (finder.getId() == 0)
			res = finder;
		else {
			res = this.findOne(finder.getId());
			res.setMinimumSalary(finder.getMinimumSalary());
			res.setKeyword(finder.getKeyword());
			res.setDeadline(finder.getDeadline());
			res.setMaximumDeadline(finder.getMaximumDeadline());
		}
		this.validator.validate(res, binding);
		if (binding.hasErrors())
			throw new ValidationException();

		return res;
	}
}
