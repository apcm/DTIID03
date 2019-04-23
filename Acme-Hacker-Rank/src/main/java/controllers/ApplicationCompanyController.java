
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ApplicationService;
import services.MessageService;
import domain.Actor;
import domain.Application;

@Controller
@RequestMapping("/application/company")
public class ApplicationCompanyController {

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private MessageService		messageService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Collection<Application> applications = this.applicationService.getMyAppList();

		final Collection<Application> applicationsS = this.applicationService.getAS(applications);
		final Collection<Application> applicationsA = this.applicationService.getAA(applications);
		final Collection<Application> applicationsR = this.applicationService.getAR(applications);

		result = new ModelAndView("application/company/list");
		result.addObject("applicationsS", applicationsS);
		result.addObject("applicationsA", applicationsA);
		result.addObject("applicationsR", applicationsR);
		result.addObject("requestURI", "/application/company/list.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;

		application = this.applicationService.findOne(applicationId);

		if (!this.applicationService.checkApplicationCompany(application))
			return new ModelAndView("redirect:/welcome/index.do");

		if (!this.applicationService.checkApplication(application))
			return new ModelAndView("redirect:list.do");

		result = this.createEditModelAndView(application);
		result.addObject("application", application);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Application application, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(application);
		else
			try {
				if (!this.applicationService.checkApplicationCompany(application))
					return new ModelAndView("redirect:/welcome/index.do");

				final Application a = this.applicationService.saveStatus(application);
				result = new ModelAndView("redirect:list.do");
				//Send the message (A-Level requirement)
				final Actor actual = this.actorService.findByPrincipal();
				this.messageService.sendApplicationStatusChangeMessage(actual, a.getStatus());
				this.messageService.sendApplicationStatusChangeMessage(a.getHacker(), a.getStatus());
				//END
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(application, "application.commit.error");
			}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Application application) {
		ModelAndView result;
		result = this.createEditModelAndView(application, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Application application, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("application/company/edit");
		result.addObject("application", application);

		result.addObject("message", messageCode);

		return result;
	}

	//-------------------------DISPLAY-----------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;

		application = this.applicationService.findOne(applicationId);
		Assert.notNull(application);

		if (!this.applicationService.checkApplicationCompany(application))
			return new ModelAndView("redirect:/welcome/index.do");

		result = this.createDisplayModelAndView(application);

		return result;
	}

	protected ModelAndView createDisplayModelAndView(final Application application) {
		ModelAndView result;
		result = this.createDisplayModelAndView(application, null);

		return result;
	}

	protected ModelAndView createDisplayModelAndView(final Application application, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("application/company/display");
		result.addObject("application", application);
		result.addObject("messageCode", messageCode);

		return result;

	}

}
