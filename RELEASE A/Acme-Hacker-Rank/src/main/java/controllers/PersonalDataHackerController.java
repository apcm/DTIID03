
package controllers;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PersonalDataService;
import domain.PersonalData;

@Controller
@RequestMapping("/personalData/hacker")
public class PersonalDataHackerController {

	@Autowired
	PersonalDataService	personalDataService;


	//crear
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculaId) {
		final ModelAndView res;
		final PersonalData pd = this.personalDataService.create(curriculaId);

		res = this.createEditModelAndView(pd);

		return res;
	}

	private ModelAndView createEditModelAndView(final PersonalData pd) {
		ModelAndView res;
		res = this.createEditModelAndView(pd, null);

		return res;
	}

	private ModelAndView createEditModelAndView(final PersonalData pd, final String messageCode) {
		ModelAndView res;
		res = new ModelAndView("personalData/edit");
		Assert.notNull(pd);

		res.addObject("personalData", pd);
		res.addObject("message", messageCode);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView save(@RequestParam final int personalDataId) {
		ModelAndView res;
		final PersonalData pd = this.personalDataService.findOne(personalDataId);

		res = new ModelAndView("personalData/edit");

		res.addObject("personalData", pd);
		Assert.notNull(pd);
		res = this.createEditModelAndView(pd);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(PersonalData pd, final BindingResult binding) {
		ModelAndView res;

		try {
			//Compruebo que no se intente editar una copia
			if (pd.getId() != 0)
				Assert.isTrue(!this.personalDataService.findOne(pd.getId()).getCurricula().getIsCopy(), "errorCopy");
			pd = this.personalDataService.reconstruct(pd, binding);
			this.personalDataService.save(pd);

			String redirect = "curricula/hacker/show.do?curriculaId=";
			redirect = redirect + String.valueOf(pd.getCurricula().getId());
			res = new ModelAndView("redirect:../../" + redirect);
		} catch (final ValidationException oops) {
			res = this.createEditModelAndView(pd);
		} catch (final Throwable oops) {
			if (oops.getMessage() == "errorCopy")
				res = this.createEditModelAndView(pd, "personalData.copy.error");
			else
				res = this.createEditModelAndView(pd, "error.personalData");
		}

		return res;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final PersonalData ed, final BindingResult binding) {
		ModelAndView res;

		try {
			String redirect = "curricula/hacker/show.do?curriculaId=";
			final PersonalData ed1 = this.personalDataService.findOne(ed.getId());
			redirect = redirect + String.valueOf(ed1.getCurricula().getId());

			this.personalDataService.delete(ed1);

			res = new ModelAndView("redirect:../../" + redirect);
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(ed, "error.personalData");
		}

		return res;
	}

}
