
package controllers.hacker;

import java.util.Collection;
import java.util.List;

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
import services.ApplicationService;
import services.HackerService;
import services.PositionService;
import domain.Application;
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

		final Application a = this.as.findOne(applicationId);

		try {
			Assert.isTrue(a.getStatus().equals("PENDING"));
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

		final Application a = this.as.create();
		final Position p = this.ps.findOne(positionId);
		final Hacker h = this.hs.getHackerByUserAccount(LoginService.getPrincipal().getId());
		final Collection<Application> appsByHacker = this.as.getApplicationsByHacker(h);

		for (final Application app : appsByHacker)
			if (app.getPosition().equals(p)) {
				result = new ModelAndView("redirect:/position/hacker/list.do"); //TODO: ESTO DEBE REDIRECCIONAR A LA LISTA DE POSITIONS QUE NO ESTA IMPLEMENTADA TODAVIA
				return result;
			}

		a.setHacker(h);
		a.setPosition(p);
		a.setStatus("created");

		this.as.firstSave(a);

		result = new ModelAndView("redirect:/application/hacker/list.do");
		return result;

	}

}
