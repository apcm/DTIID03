
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculaService;
import services.EducationDataService;
import services.HackerService;
import domain.EducationData;

@Controller
@RequestMapping("/educationData/hacker")
public class EducationDataHackerController {

	@Autowired
	HackerService			hackerService;

	@Autowired
	CurriculaService		curriculaService;

	@Autowired
	EducationDataService	educationDataService;


	//crear
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculaId) {
		final ModelAndView res;
		final EducationData ed = this.educationDataService.create(curriculaId);

		res = this.createEditModelAndView(ed);

		return res;
	}

	private ModelAndView createEditModelAndView(final EducationData ed) {
		ModelAndView res;
		res = this.createEditModelAndView(ed, null);

		return res;
	}

	private ModelAndView createEditModelAndView(final EducationData ed, final String messageCode) {
		ModelAndView res;
		res = new ModelAndView("educationData/edit");

		res.addObject("educationData", ed);
		res.addObject("message", messageCode);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView save(@RequestParam final int educationDataId) {
		ModelAndView res;
		final EducationData ed = this.educationDataService.findOne(educationDataId);

		res = new ModelAndView("educationData/edit");

		res.addObject("educationData", ed);
		Assert.notNull(ed);
		res = this.createEditModelAndView(ed);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EducationData ed, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(ed);
		else

			try {

				this.educationDataService.save(ed);

				String redirect = "redirect:show.do?";
				redirect = redirect + String.valueOf(ed.getCurricula().getId());
				res = new ModelAndView(redirect);
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(ed, "error.educationData");
			}

		return res;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final EducationData ed, final BindingResult binding) {
		ModelAndView res;

		try {
			String redirect = "redirect:show.do?";
			redirect = redirect + String.valueOf(ed.getCurricula().getId());
			final EducationData ed1 = this.educationDataService.findOne(ed.getId());
			this.educationDataService.delete(ed1);

			res = new ModelAndView(redirect);
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(ed, "error.educationData");
		}

		return res;
	}

}
