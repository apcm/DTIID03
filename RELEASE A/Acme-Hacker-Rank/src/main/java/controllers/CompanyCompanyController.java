
package controllers;

import java.util.concurrent.TimeUnit;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccount;
import services.CompanyService;
import domain.Company;

@Controller
@RequestMapping("/company/company")
public class CompanyCompanyController extends AbstractController {

	@Autowired
	CompanyService	companyService;


	// Constructors -----------------------------------------------------------

	public CompanyCompanyController() {
		super();
	}

	////////////////////////////
	//////////EDIT//////////////
	////////////////////////////

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editEdit() {
		ModelAndView res;
		Company company;

		company = this.companyService.findByPrincipal();
		//company = this.companyService.findOne(companyId);
		res = this.createEditEditModelAndView(company);
		return res;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute Company company, final BindingResult binding) {
		ModelAndView result;

		try {
			final String vacia = "";
			if (!company.getEmail().isEmpty() || company.getEmail() != vacia)
				Assert.isTrue(company.getEmail().matches("^[A-z0-9]+@[A-z0-9.]+$") || company.getEmail().matches("^[A-z0-9 ]+ <[A-z0-9]+@[A-z0-9.]+>$"), "Wrong email");

			company = this.companyService.reconstruct(company, binding);
			TimeUnit.SECONDS.sleep(1);

			this.companyService.save(company);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final ValidationException oops) {
			result = this.createEditEditModelAndView(company);
		} catch (final Throwable oops) {
			if (oops.getMessage() == "Wrong email")
				result = this.createEditEditModelAndView(company, "company.email.error");
			else
				result = this.createEditEditModelAndView(company, "company.comit.error");
		}

		return result;

	}
	protected ModelAndView createEditEditModelAndView(final Company company) {
		ModelAndView result;

		result = this.createEditEditModelAndView(company, null);

		return result;

	}

	protected ModelAndView createEditEditModelAndView(final Company company, final String message) {
		ModelAndView result;

		UserAccount userAccount;
		userAccount = company.getUserAccount();

		result = new ModelAndView("company/edit");
		result.addObject("company", company);
		result.addObject("message", message);
		result.addObject("userAccount", userAccount);
		return result;
	}

	////////////////////////////
	//////////SHOW//////////////
	////////////////////////////

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView res;
		Company company;

		company = this.companyService.findByPrincipal();
		//company = this.companyService.findOne(companyId);
		res = this.createShowModelAndView(company);
		return res;

	}

	protected ModelAndView createShowModelAndView(final Company company) {
		ModelAndView result;

		result = this.createShowModelAndView(company, null);

		return result;

	}

	protected ModelAndView createShowModelAndView(final Company company, final String message) {
		ModelAndView result;
		//		Collection<Box> boxes;
		//		final Collection<SocialProfile> socialProfiles;
		//		final Collection<Endorsement> endorsements;
		//		final Collection<FixUpTask> fixUpTasks;
		UserAccount userAccount;

		//		fixUpTasks = company.getFixUpTasks();
		//		boxes = company.getBoxes();
		//		socialProfiles = company.getSocialProfiles();
		//		endorsements = company.getEndorsements();
		userAccount = company.getUserAccount();
		//		 if (socialProfiles.isEmpty())
		//		 * socialProfiles = null;
		//		 * if (endorsements.isEmpty())
		//		 * endorsements = null;
		//if (boxes.isEmpty())
		//	boxes = null;

		result = new ModelAndView("company/show");
		result.addObject("company", company);
		//		result.addObject("boxes", boxes);
		//		result.addObject("socialProfiles", socialProfiles);
		result.addObject("message", message);
		//		result.addObject("endorsements", endorsements);
		//		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("userAccount", userAccount);
		return result;
	}

	////////////////////////////
	//////////DELETE//////////////
	////////////////////////////

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "leave")
	public ModelAndView saveLeave(final Company company, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditEditModelAndView(company);
		else
			try {

				this.companyService.leave();
				result = new ModelAndView("redirect:../../j_spring_security_logout");
			} catch (final Throwable error) {
				if (error.getMessage() == "Wrong email")
					result = this.createEditEditModelAndView(company, "company.email.error");
				else
					result = this.createEditEditModelAndView(company, "company.comit.error");
				System.out.println(error.getMessage());
			}

		return result;

	}

}
