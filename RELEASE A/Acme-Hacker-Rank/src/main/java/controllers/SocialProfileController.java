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

import services.SocialProfileService;

import domain.SocialProfile;

@Controller
@RequestMapping("/socialprofile")
public class SocialProfileController {

	@Autowired
	private SocialProfileService socialprofileService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Collection<SocialProfile> profiles = this.socialprofileService
				.findMyProfiles();

		result = new ModelAndView("socialprofile/list");
		result.addObject("profiles", profiles);
		result.addObject("requestURI", "/socialprofiles/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		SocialProfile socialProfile;

		socialProfile = this.socialprofileService.create();

		result = this.createEditModelAndView(socialProfile);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int profileId) {
		ModelAndView result;
		SocialProfile socialProfile;

		socialProfile = this.socialprofileService.findOne(profileId);
		Assert.notNull(socialProfile);
		result = this.createEditModelAndView(socialProfile);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SocialProfile socialProfile,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(socialProfile);
		else
			try {
				this.socialprofileService.saveMyProfile(socialProfile);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(socialProfile,
						"profile.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final SocialProfile socialProfile,
			final BindingResult binding) {
		ModelAndView result;

		try {
			this.socialprofileService.delete(socialProfile);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(socialProfile);
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(SocialProfile socialProfile) {
		ModelAndView result;
		result = this.createEditModelAndView(socialProfile, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(SocialProfile socialProfile,
			String messageCode) {
		ModelAndView result;
		Collection<SocialProfile> list;

		list = this.socialprofileService.findAll();
		if (list.contains(socialProfile))
			list.remove(socialProfile);

		result = new ModelAndView("socialprofile/edit");
		result.addObject("socialProfile", socialProfile);
		result.addObject("list", list);

		result.addObject("message", messageCode);

		return result;
	}

	// -------------------------DISPLAY-----------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int profileId) {
		ModelAndView result;
		SocialProfile profile;

		profile = this.socialprofileService.findOne(profileId);
		Assert.notNull(profile);

		result = this.createDisplayModelAndView(profile);

		return result;
	}

	protected ModelAndView createDisplayModelAndView(
			final SocialProfile socialProfile) {
		ModelAndView result;
		result = this.createDisplayModelAndView(socialProfile, null);

		return result;
	}

	protected ModelAndView createDisplayModelAndView(
			final SocialProfile socialProfile, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("socialprofile/display");
		result.addObject("socialProfile", socialProfile);
		result.addObject("messageCode", messageCode);

		return result;

	}

}
