
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
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.UserAccount;
import services.HackerService;
import domain.Hacker;
import forms.HackerForm;

@Controller
@RequestMapping("/hacker")
public class HackerController extends AbstractController {

	@Autowired
	HackerService	hackerService;


	// Constructors -----------------------------------------------------------

	public HackerController() {
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
		authority.setAuthority(Authority.HACKER);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		final HackerForm hackerForm = new HackerForm();
		hackerForm.setUserAccount(userAccount);
		res = this.createEditModelAndView(hackerForm);

		//hacker = this.hackerService.create();
		//res = this.createEditModelAndView(hacker);
		return res;

	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute final HackerForm hackerForm, final BindingResult binding) {
		ModelAndView result;

		try {
			Assert.isTrue(hackerForm.isConditionsAccepted(), "conditionsAccepted");
			final Hacker hacker = this.hackerService.reconstruct(hackerForm, binding);
			final String vacia = "";
			if (!hacker.getEmail().isEmpty() || hacker.getEmail() != vacia)
				Assert.isTrue(hacker.getEmail().matches("^[A-z0-9]+@[A-z0-9.]+$") || hacker.getEmail().matches("^[A-z0-9 ]+ <[A-z0-9]+@[A-z0-9.]+>$"), "Wrong email");

			this.hackerService.save(hacker);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final ValidationException oops) {
			result = this.createEditModelAndView(hackerForm);
		} catch (final Throwable oops) {
			if (oops.getMessage() == "Wrong email")
				result = this.createEditModelAndView(hackerForm, "hacker.email.error");
			else if (oops.getMessage() == "conditionsAccepted")
				result = this.createEditModelAndView(hackerForm, "hacker.conditionsError");
			else
				result = this.createEditModelAndView(hackerForm, "hacker.comit.error");
		}
		return result;

	}

	// Ancillary methods ------------------------------
	protected ModelAndView createEditModelAndView(final HackerForm hackerForm) {
		ModelAndView result;

		result = this.createEditModelAndView(hackerForm, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final HackerForm hackerForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("hacker/register");
		result.addObject("hackerForm", hackerForm);
		result.addObject("message", message);

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView res;

		final Collection<Hacker> hackers = this.hackerService.findAll();

		res = new ModelAndView("hacker/list");
		res.addObject("requestURI", "hacker/list.do");
		res.addObject("hackers", hackers);
		return res;
	}
	//
	//	@RequestMapping(value = "/showRecords", method = RequestMethod.GET)
	//	public ModelAndView showRecords(@RequestParam final int hackerId) {
	//		ModelAndView res;
	//		Hacker hacker;
	//
	//		//hacker = this.hackerService.findByPrincipal();
	//		hacker = this.hackerService.findOne(hackerId);
	//		res = this.createShowRecordsModelAndView(hacker);
	//		return res;
	//
	//	}
	//
	//	protected ModelAndView createShowRecordsModelAndView(final Hacker hacker) {
	//		ModelAndView result;
	//
	//		result = this.createShowRecordsModelAndView(hacker, null);
	//
	//		return result;
	//
	//	}

	//	protected ModelAndView createShowRecordsModelAndView(final Hacker hacker, final String message) {
	//		final ModelAndView result;
	//
	//		UserAccount userAccount;
	//		userAccount = hacker.getUserAccount();
	//
	//		final Collection<LegalRecord> legalRecords = hacker.getLegalRecords();
	//		final Collection<PeriodRecord> periodRecords = hacker.getPeriodRecords();
	//		final InceptionRecord inceptionRecord = hacker.getInceptionRecord();
	//		final Collection<LinkRecord> linkRecords = hacker.getLinkRecords();
	//
	//		result = new ModelAndView("hacker/showRecords");
	//		result.addObject("hacker", hacker);
	//		result.addObject("legalRecords", legalRecords);
	//		result.addObject("periodRecords", periodRecords);
	//		result.addObject("inceptionRecord", inceptionRecord);
	//		result.addObject("linkRecords", linkRecords);
	//		result.addObject("message", message);
	//		result.addObject("userAccount", userAccount);
	//		return result;
	//	}

}
