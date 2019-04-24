
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

import services.PositionDataService;
import domain.PositionData;

@Controller
@RequestMapping("/positionData/hacker")
public class PositionDataHackerController {

	@Autowired
	PositionDataService	positionDataService;


	//crear
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculaId) {
		final ModelAndView res;
		final PositionData pd = this.positionDataService.create(curriculaId);

		res = this.createEditModelAndView(pd);

		return res;
	}

	private ModelAndView createEditModelAndView(final PositionData pd) {
		ModelAndView res;
		res = this.createEditModelAndView(pd, null);

		return res;
	}

	private ModelAndView createEditModelAndView(final PositionData pd, final String messageCode) {
		ModelAndView res;
		res = new ModelAndView("positionData/edit");
		Assert.notNull(pd);

		res.addObject("positionData", pd);
		res.addObject("message", messageCode);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView save(@RequestParam final int positionDataId) {
		ModelAndView res;
		final PositionData pd = this.positionDataService.findOne(positionDataId);

		res = new ModelAndView("positionData/edit");

		res.addObject("positionData", pd);
		Assert.notNull(pd);
		res = this.createEditModelAndView(pd);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(PositionData pd, final BindingResult binding) {
		ModelAndView res;

		try {
			pd = this.positionDataService.reconstruct(pd, binding);
			this.positionDataService.save(pd);

			String redirect = "curricula/hacker/show.do?curriculaId=";
			redirect = redirect + String.valueOf(pd.getCurricula().getId());
			res = new ModelAndView("redirect:../../" + redirect);
		} catch (final ValidationException oops) {
			res = this.createEditModelAndView(pd);
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(pd, "error.positionData");
		}

		return res;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final PositionData ed, final BindingResult binding) {
		ModelAndView res;

		try {
			String redirect = "curricula/hacker/show.do?curriculaId=";
			final PositionData ed1 = this.positionDataService.findOne(ed.getId());
			redirect = redirect + String.valueOf(ed1.getCurricula().getId());

			this.positionDataService.delete(ed1);

			res = new ModelAndView("redirect:../../" + redirect);
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(ed, "error.positionData");
		}

		return res;
	}

}
