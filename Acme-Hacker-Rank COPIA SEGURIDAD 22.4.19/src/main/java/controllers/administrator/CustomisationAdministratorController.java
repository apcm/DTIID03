
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomisationService;
import controllers.AbstractController;
import domain.Customisation;

@Controller
@RequestMapping("customisation/administrator")
public class CustomisationAdministratorController extends AbstractController {

	@Autowired
	CustomisationService	service;


	@RequestMapping("/edit")
	public ModelAndView editCustomisation() {
		ModelAndView res;
		final Customisation c = this.service.getCustomisation();
		Assert.notNull(c);
		res = this.createEditModelAndView(c);
		return res;
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Customisation c, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(c);
		else
			try {
				this.service.save(c);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(c, "customisation.commit.error");
			}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Customisation customisation) {
		ModelAndView result;

		result = this.createEditModelAndView(customisation, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Customisation customisation, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("customisation/edit");
		result.addObject("customisation", customisation);
		result.addObject("message", messageCode);
		return result;
	}
}
