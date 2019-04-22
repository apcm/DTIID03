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

import domain.Application;


import services.ApplicationService;


@Controller
@RequestMapping("/application/company")
public class ApplicationCompanyController {
	
	
	@Autowired
	private ApplicationService	applicationService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		Collection<Application> applications = this.applicationService.getMyAppList();
		
		Collection<Application> applicationsP = this.applicationService.getAP(applications);
		Collection<Application> applicationsA = this.applicationService.getAA(applications);
		Collection<Application> applicationsR = this.applicationService.getAR(applications);

		result = new ModelAndView("application/company/list");
		result.addObject("applicationsP", applicationsP);
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
		
		if(!this.applicationService.checkApplicationCompany(application)){
			return new ModelAndView("redirect:/welcome/index.do");
		}
		
		if(!this.applicationService.checkApplication(application)){
			return new ModelAndView("redirect:list.do");
		}

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
				if(!this.applicationService.checkApplicationCompany(application)){
					return new ModelAndView("redirect:/welcome/index.do");
				}
				
				this.applicationService.saveStatus(application);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(application, "application.commit.error");
			}

		return result;
	}
	

	protected ModelAndView createEditModelAndView(Application application) {
		ModelAndView result;
		result = this.createEditModelAndView(application, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Application application, String messageCode) {
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
			
			if(!this.applicationService.checkApplicationCompany(application)){
				return new ModelAndView("redirect:/welcome/index.do");
			}

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
