
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ApplicationRepository;
import repositories.CompanyRepository;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Company;
import domain.Hacker;
import domain.Position;
import domain.Problem;

@Service
@Transactional
public class ApplicationService {

	@Autowired
	private ApplicationRepository	ar;

	@Autowired
	private Validator				validator;

	@Autowired
	private PositionService			ps;

	@Autowired
	private HackerService			hs;

	@Autowired
	private CompanyRepository		cr;


	public List<Application> getApplicationsByHacker(final Hacker h) {
		return this.ar.getApplicationsByHacker(h);
	}

	public Application findOne(final int id) {
		return this.ar.findOne(id);
	}

	public Application create() {
		final Application a = new Application();
		a.setStatus("PENDING");
		a.setAnswerExplanation("");
		return a;
	}

	public Application save(final Application a) {
		if (a.getStatus().equals("PENDING"))
			try {
				Assert.notNull(a.getLink());
				final Date moment = new Date();
				moment.setSeconds(moment.getSeconds() - 1);
				a.setMoment(moment);
				a.setStatus("SUBMITTED");
			} catch (final Throwable oops) {
				return null;
			}

		final Application res = this.ar.save(a);
		return res;

	}

	public Application firstSave(final Application a) {
		//TODO: FIX ERROR WITH NOT NULLS AND STUFF
		a.setStatus("PENDING");
		final Date moment = new Date();
		moment.setSeconds(moment.getSeconds() - 1);
		a.setMoment(moment);
		final Problem p = this.getRandomProblemByApplication(a);
		a.setProblem(p);

		Assert.isTrue(this.checkHackerApplications(a.getPosition().getId()));

		final Application res = this.ar.save(a);
		this.ar.flush();
		return res;

	}
	public boolean checkHackerApplications(final int positionId) {
		boolean result = true;
		final Position p = this.ps.findOne(positionId);
		final Hacker h = this.hs.getHackerByUserAccount(LoginService.getPrincipal().getId());
		final Collection<Application> appsByHacker = this.getApplicationsByHacker(h);

		for (final Application app : appsByHacker)
			if (app.getPosition().equals(p) && !app.getStatus().equals("REJECTED")) {
				result = false;
				return result;
			}

		return result;
	}
	private Problem getRandomProblemByApplication(final Application a) {
		final List<Problem> allProblems = (List<Problem>) a.getPosition().getProblems();
		final int size = allProblems.size();

		final int randomNumber = this.generateRandomInt(0, size - 1);

		return allProblems.get(randomNumber);
	}

	private int generateRandomInt(final int i, final int j) {
		final int x = (int) (Math.random() * ((j - i) + 1)) + i;

		return x;
	}

	public Application reconstructSolveHacker(final Application application, final BindingResult binding) {
		Application res;

		if (application.getId() == 0)
			res = application;
		else
			res = this.ar.findOne(application.getId());
		res.setAnswerExplanation(application.getAnswerExplanation());
		res.setLink(application.getLink());
		this.validator.validate(res, binding);
		if (binding.hasErrors())
			throw new ValidationException();
		try {
			Assert.isTrue(this.ownValidator(res));
		} catch (final Throwable oops) {
			throw new ValidationException();
		}

		return res;

	}

	private boolean ownValidator(final Application a) {
		boolean res = true;
		try {
			Assert.isTrue(a.getAnswerExplanation() != null);
			Assert.isTrue(!a.getAnswerExplanation().isEmpty());
			Assert.isTrue(a.getLink() != null);
			Assert.isTrue(!a.getLink().isEmpty());
		} catch (final Throwable oops) {
			res = false;
		}

		return res;
	}

	public Collection<Application> getMyAppList() {
		final Collection<Application> res = new ArrayList<Application>();
		final Collection<Application> all = this.findAll();
		for (final Application a : all)
			if (a.getPosition().getCompany().equals(this.getThisCompany()))
				res.add(a);

		return res;
	}

	public Collection<Application> findAll() {
		return this.ar.findAll();
	}

	public Company getThisCompany() {
		Company res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.cr.findByUserAccountId(userAccount.getId());
		return res;
	}

	public Collection<Application> getAP(final Collection<Application> applications) {
		final Collection<Application> res = new ArrayList<Application>();
		for (final Application a : applications)
			if (a.getStatus().equals("PENDING"))
				res.add(a);
		return res;
	}

	public Collection<Application> getAS(final Collection<Application> applications) {
		final Collection<Application> res = new ArrayList<Application>();
		for (final Application a : applications)
			if (a.getStatus().equals("SUBMITTED"))
				res.add(a);
		return res;
	}

	public Collection<Application> getAA(final Collection<Application> applications) {
		final Collection<Application> res = new ArrayList<Application>();
		for (final Application a : applications)
			if (a.getStatus().equals("ACCEPTED"))
				res.add(a);
		return res;
	}

	public Collection<Application> getAR(final Collection<Application> applications) {
		final Collection<Application> res = new ArrayList<Application>();
		for (final Application a : applications)
			if (a.getStatus().equals("REJECTED"))
				res.add(a);
		return res;
	}

	public Application saveStatus(final Application application) {
		Assert.isTrue(application.getStatus().equals("ACCEPTED") || application.getStatus().equals("REJECTED"));
		Application res = new Application();
		res = this.ar.save(application);
		return res;
	}

	public boolean checkApplication(final Application application) {
		Boolean res = true;

		if (application.getStatus().equals("ACCEPTED") || application.getStatus().equals("REJECTED"))
			res = false;

		return res;
	}

	public boolean checkApplicationCompany(final Application application) {
		Boolean res = true;

		if (!application.getPosition().getCompany().equals(this.getThisCompany()))
			res = false;

		return res;
	}

}
