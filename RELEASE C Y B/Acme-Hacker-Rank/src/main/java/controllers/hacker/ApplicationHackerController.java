
package controllers.hacker;

import java.util.Collection;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.ApplicationService;
import services.CurriculaService;
import services.HackerService;
import services.MessageService;
import services.PositionService;
import domain.Actor;
import domain.Application;
import domain.Curricula;
import domain.Hacker;
import domain.Position;

@Controller
@RequestMapping("/application/hacker")
public class ApplicationHackerController {

	@Autowired
	private ApplicationService	as;

	@Autowired
	private HackerService		hs;

	@Autowired
	private PositionService		ps;

	@Autowired
	private CurriculaService	curriculaService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private MessageService		messageService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final UserAccount ua = LoginService.getPrincipal();
		final Hacker h = this.hs.getHackerByUserAccount(ua.getId());

		final List<Application> applications = this.as.getApplicationsByHacker(h);
		result = new ModelAndView("application/hacker/list");
		result.addObject("applications", applications);
		result.addObject("requestURI", "application/hacker/list.do");

		return result;

	}

	@RequestMapping(value = "/solve", method = RequestMethod.GET)
	public ModelAndView solve(@RequestParam final int applicationId) {
		final ModelAndView result;
		final Hacker h = this.hs.getHackerByUserAccount(LoginService.getPrincipal().getId());
		final Application a = this.as.findOne(applicationId);

		try {
			Assert.isTrue(a.getStatus().equals("PENDING"));
			Assert.isTrue(a.getHacker().getId() == h.getId());
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/application/hacker/list.do");
			return result;
		}

		result = this.createSolveModelAndView(a);

		return result;

	}

	@RequestMapping(value = "/solve", method = RequestMethod.POST, params = "save")
	public ModelAndView solve(@ModelAttribute Application application, final BindingResult binding) {
		ModelAndView result;
		try {
			application = this.as.reconstructSolveHacker(application, binding);
			final Application a = this.as.save(application);
			if (a == null) {
				result = this.createSolveModelAndView(application, "application.error.commit.emptyValues");
				return result;
			}
			//Send the message (A-Level requirement)
			final Actor actual = this.actorService.findByPrincipal();
			this.messageService.sendApplicationStatusChangeMessage(actual, a.getStatus());
			this.messageService.sendApplicationStatusChangeMessage(a.getPosition().getCompany(), a.getStatus());
			//END

		} catch (final Throwable oops) {
			result = this.createSolveModelAndView(application, "application.error.commit.emptyValues");
			return result;
		}

		result = new ModelAndView("redirect:/application/hacker/list.do");

		return result;
	}

	protected ModelAndView createSolveModelAndView(final Application application) {
		ModelAndView result;

		result = this.createSolveModelAndView(application, null);

		return result;

	}

	protected ModelAndView createSolveModelAndView(final Application application, final String message) {
		final ModelAndView result;
		Application a = application;
		result = new ModelAndView("application/hacker/solve");
		if (a.getProblem() == null)
			a = this.as.findOne(a.getId());
		result.addObject("application", a);
		result.addObject("problem", a.getProblem());
		result.addObject("id", a.getId());
		result.addObject("answerExplanation", a.getAnswerExplanation());
		result.addObject("answerLink", a.getLink());
		result.addObject("message", message);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int positionId) {
		final ModelAndView result;
		final Position p = this.ps.findOne(positionId);
		final Hacker h = this.hs.getHackerByUserAccount(LoginService.getPrincipal().getId());

		final Application a = this.as.create();
		try {
			Assert.isTrue(this.as.checkHackerApplications(positionId));
		} catch (final Throwable oops) {
			//to position/hacker/list.do
			final Collection<Position> positions = this.ps.findAll();

			result = new ModelAndView("position/list");
			result.addObject("positions", positions);
			result.addObject("requestURI", "/position/hacker/list.do");
			final boolean showError = true;
			result.addObject("showError", showError);

			return result;
		}

		a.setHacker(h);
		a.setPosition(p);
		a.setStatus("created");

		final Application a2 = this.as.firstSave(a);

		final List<Curricula> lCurricula = this.curriculaService.getCurriculasFromHackerNotCopies();
		result = new ModelAndView("application/hacker/create");
		result.addObject("lC", lCurricula);
		final Curricula c = this.curriculaService.create();
		c.setApplication(a2);
		result.addObject("curricula", c);

		return result;

	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int applicationId) {
		final ModelAndView result;
		final Hacker h = this.hs.getHackerByUserAccount(LoginService.getPrincipal().getId());
		final Application a = this.as.findOne(applicationId);

		try {
			Assert.isTrue(a.getHacker().getId() == h.getId());
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/application/hacker/list.do");
			return result;
		}

		result = new ModelAndView("application/hacker/show");
		result.addObject(a);

		return result;

	}
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(final Curricula c, final BindingResult binding) {
		ModelAndView result;
		try {
			final Curricula c2 = this.curriculaService.reconstructApplicationC(c, binding);
			result = new ModelAndView("redirect:/position/hacker/list.do");
			this.curriculaService.save(c2);
		} catch (final ValidationException oops) {
			result = this.create(c.getApplication().getPosition().getId());
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/position/hacker/list.do");
		}
		return result;
	}

}
