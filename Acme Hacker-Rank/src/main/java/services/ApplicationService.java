
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
import domain.Application;
import domain.Problem;

@Service
@Transactional
public class ApplicationService {

	@Autowired
	private ApplicationRepository	ar;

	@Autowired
	private Validator				validator;


	public List<Application> getApplicationsByHacker(final int id) {
		final Collection<Application> all = this.ar.findAll();
		final List<Application> applicationsfinal = new ArrayList<Application>();
		for (final Application a : all)
			if (a.getHacker() != null)
				if (a.getHacker().getId() == id)
					applicationsfinal.add(a);

		return applicationsfinal;
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
				a.setMoment(new Date());
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
		final Application res = this.ar.save(a);
		this.ar.flush();
		return res;

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
}
