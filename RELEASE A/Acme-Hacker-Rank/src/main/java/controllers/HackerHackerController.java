
package controllers;

import java.util.Collection;
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
import services.HackerService;
import domain.Hacker;
import domain.SocialProfile;

@Controller
@RequestMapping("/hacker/hacker")
public class HackerHackerController extends AbstractController {

	@Autowired
	HackerService	hackerService;


	// Constructors -----------------------------------------------------------

	public HackerHackerController() {
		super();
	}

	// //////////////////////////
	// ////////EDIT//////////////
	// //////////////////////////

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editEdit() {
		ModelAndView res;
		Hacker hacker;

		hacker = this.hackerService.findByPrincipal();
		// hacker = this.hackerService.findOne(hackerId);
		res = this.createEditEditModelAndView(hacker);
		return res;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute Hacker hacker, final BindingResult binding) {
		ModelAndView result;

		try {
			final String vacia = "";
			if (!hacker.getEmail().isEmpty() || hacker.getEmail() != vacia)
				Assert.isTrue(hacker.getEmail().matches("^[A-z0-9]+@[A-z0-9.]+$") || hacker.getEmail().matches("^[A-z0-9 ]+ <[A-z0-9]+@[A-z0-9.]+>$"), "Wrong email");

			hacker = this.hackerService.reconstruct(hacker, binding);
			TimeUnit.SECONDS.sleep(1);

			this.hackerService.save(hacker);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final ValidationException oops) {
			result = this.createEditEditModelAndView(hacker);
		} catch (final Throwable oops) {
			if (oops.getMessage() == "Wrong email")
				result = this.createEditEditModelAndView(hacker, "hacker.email.error");
			else
				result = this.createEditEditModelAndView(hacker, "hacker.comit.error");
		}

		return result;

	}

	protected ModelAndView createEditEditModelAndView(final Hacker hacker) {
		ModelAndView result;

		result = this.createEditEditModelAndView(hacker, null);

		return result;

	}

	protected ModelAndView createEditEditModelAndView(final Hacker hacker, final String message) {
		ModelAndView result;

		UserAccount userAccount;
		userAccount = hacker.getUserAccount();

		result = new ModelAndView("hacker/edit");
		result.addObject("hacker", hacker);
		result.addObject("message", message);
		result.addObject("userAccount", userAccount);
		return result;
	}

	// //////////////////////////
	// ////////SHOW//////////////
	// //////////////////////////

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView res;
		Hacker hacker;

		hacker = this.hackerService.findByPrincipal();
		// hacker = this.hackerService.findOne(hackerId);
		res = this.createShowModelAndView(hacker);
		return res;

	}

	protected ModelAndView createShowModelAndView(final Hacker hacker) {
		ModelAndView result;

		result = this.createShowModelAndView(hacker, null);

		return result;

	}

	protected ModelAndView createShowModelAndView(final Hacker hacker, final String message) {
		ModelAndView result;
		// Collection<Box> boxes;
		Collection<SocialProfile> socialProfiles;
		UserAccount userAccount;

		// boxes = hacker.getBoxes();
		socialProfiles = hacker.getSocialProfiles();
		userAccount = hacker.getUserAccount();
		if (socialProfiles.isEmpty())
			socialProfiles = null;
		// if (boxes.isEmpty())
		// boxes = null;

		result = new ModelAndView("hacker/show");
		result.addObject("hacker", hacker);
		// result.addObject("boxes", boxes);
		result.addObject("socialProfiles", socialProfiles);
		result.addObject("message", message);
		result.addObject("userAccount", userAccount);
		return result;
	}

	// //////////////////////////
	// ////////DELETE//////////////
	// //////////////////////////

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "leave")
	public ModelAndView saveLeave(final Hacker hacker, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditEditModelAndView(hacker);
		else
			try {

				this.hackerService.leave();
				result = new ModelAndView("redirect:../../j_spring_security_logout");
			} catch (final Throwable error) {
				if (error.getMessage() == "Wrong email")
					result = this.createEditEditModelAndView(hacker, "hacker.email.error");
				else
					result = this.createEditEditModelAndView(hacker, "hacker.comit.error");
				System.out.println(error.getMessage());
			}

		return result;

	}

}
