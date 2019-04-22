
package controllers;

import java.util.Collection;

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

import security.Authority;
import security.UserAccount;
import services.CompanyService;
import domain.Company;
import forms.CompanyForm;

@Controller
@RequestMapping("/company")
public class CompanyController extends AbstractController {

	@Autowired
	CompanyService	companyService;


	// Constructors -----------------------------------------------------------

	public CompanyController() {
		super();
	}

	// Edition ------------------------------
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView res;

		Authority authority;
		Collection<Authority> authorities;
		UserAccount userAccount;
		userAccount = new UserAccount();
		authorities = userAccount.getAuthorities();
		authority = new Authority();
		authority.setAuthority(Authority.COMPANY);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		final CompanyForm companyForm = new CompanyForm();
		companyForm.setUserAccount(userAccount);
		res = this.createEditModelAndView(companyForm);

		//company = this.companyService.create();
		//res = this.createEditModelAndView(company);
		return res;

	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute final CompanyForm companyForm, final BindingResult binding) {
		ModelAndView result;

		try {
			Assert.isTrue(companyForm.isConditionsAccepted(), "conditionsAccepted");
			final Company company = this.companyService.reconstruct(companyForm, binding);
			final String vacia = "";
			if (!company.getEmail().isEmpty() || company.getEmail() != vacia)
				Assert.isTrue(company.getEmail().matches("^[A-z0-9]+@[A-z0-9.]+$") || company.getEmail().matches("^[A-z0-9 ]+ <[A-z0-9]+@[A-z0-9.]+>$"), "Wrong email");

			this.companyService.save(company);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final ValidationException oops) {
			result = this.createEditModelAndView(companyForm);
		} catch (final Throwable oops) {
			if (oops.getMessage() == "Wrong email")
				result = this.createEditModelAndView(companyForm, "company.email.error");
			else if (oops.getMessage() == "conditionsAccepted")
				result = this.createEditModelAndView(companyForm, "company.conditionsError");
			else
				result = this.createEditModelAndView(companyForm, "company.comit.error");
		}
		return result;

	}

	// Ancillary methods ------------------------------
	protected ModelAndView createEditModelAndView(final CompanyForm companyForm) {
		ModelAndView result;

		result = this.createEditModelAndView(companyForm, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final CompanyForm companyForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("company/register");
		result.addObject("companyForm", companyForm);
		result.addObject("message", message);

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView res;

		final Collection<Company> companys = this.companyService.findAll();

		res = new ModelAndView("company/list");
		res.addObject("requestURI", "company/list.do");
		res.addObject("companys", companys);
		return res;
	}
	//
	//	@RequestMapping(value = "/showRecords", method = RequestMethod.GET)
	//	public ModelAndView showRecords(@RequestParam final int companyId) {
	//		ModelAndView res;
	//		Company company;
	//
	//		//company = this.companyService.findByPrincipal();
	//		company = this.companyService.findOne(companyId);
	//		res = this.createShowRecordsModelAndView(company);
	//		return res;
	//
	//	}
	//
	//	protected ModelAndView createShowRecordsModelAndView(final Company company) {
	//		ModelAndView result;
	//
	//		result = this.createShowRecordsModelAndView(company, null);
	//
	//		return result;
	//
	//	}

	//	protected ModelAndView createShowRecordsModelAndView(final Company company, final String message) {
	//		final ModelAndView result;
	//
	//		UserAccount userAccount;
	//		userAccount = company.getUserAccount();
	//
	//		final Collection<LegalRecord> legalRecords = company.getLegalRecords();
	//		final Collection<PeriodRecord> periodRecords = company.getPeriodRecords();
	//		final InceptionRecord inceptionRecord = company.getInceptionRecord();
	//		final Collection<LinkRecord> linkRecords = company.getLinkRecords();
	//
	//		result = new ModelAndView("company/showRecords");
	//		result.addObject("company", company);
	//		result.addObject("legalRecords", legalRecords);
	//		result.addObject("periodRecords", periodRecords);
	//		result.addObject("inceptionRecord", inceptionRecord);
	//		result.addObject("linkRecords", linkRecords);
	//		result.addObject("message", message);
	//		result.addObject("userAccount", userAccount);
	//		return result;
	//	}
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam int companyId) {
		ModelAndView res;
		Company company;

		company = this.companyService.findOne(companyId);
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

		//		boxes = company.getBoxes();
		//		socialProfiles = company.getSocialProfiles();
		//		endorsements = company.getEndorsements();
		
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
		
		return result;
	}
}
