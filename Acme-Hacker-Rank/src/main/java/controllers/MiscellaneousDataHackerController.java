
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

import services.MiscellaneousDataService;
import domain.MiscellaneousData;

@Controller
@RequestMapping("/miscellaneousData/hacker")
public class MiscellaneousDataHackerController {

	@Autowired
	MiscellaneousDataService	miscellaneousDataService;


	//crear
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculaId) {
		final ModelAndView res;
		final MiscellaneousData pd = this.miscellaneousDataService.create(curriculaId);

		res = this.createEditModelAndView(pd);

		return res;
	}

	private ModelAndView createEditModelAndView(final MiscellaneousData pd) {
		ModelAndView res;
		res = this.createEditModelAndView(pd, null);

		return res;
	}

	private ModelAndView createEditModelAndView(final MiscellaneousData pd, final String messageCode) {
		ModelAndView res;
		res = new ModelAndView("miscellaneousData/edit");
		Assert.notNull(pd);

		res.addObject("miscellaneousData", pd);
		res.addObject("message", messageCode);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView save(@RequestParam final int miscellaneousDataId) {
		ModelAndView res;
		final MiscellaneousData pd = this.miscellaneousDataService.findOne(miscellaneousDataId);

		res = new ModelAndView("miscellaneousData/edit");

		res.addObject("miscellaneousData", pd);
		Assert.notNull(pd);
		res = this.createEditModelAndView(pd);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(MiscellaneousData pd, final BindingResult binding) {
		ModelAndView res;

		try {
			if (pd.getId() != 0)
				Assert.isTrue(!this.miscellaneousDataService.findOne(pd.getId()).getCurricula().getIsCopy(), "errorCopy");
			pd = this.miscellaneousDataService.reconstruct(pd, binding);
			this.miscellaneousDataService.save(pd);

			String redirect = "curricula/hacker/show.do?curriculaId=";
			redirect = redirect + String.valueOf(pd.getCurricula().getId());
			res = new ModelAndView("redirect:../../" + redirect);
		} catch (final ValidationException oops) {
			res = this.createEditModelAndView(pd);
		} catch (final Throwable oops) {
			if (oops.getMessage() == "errorCopy")
				res = this.createEditModelAndView(pd, "error.copy.miscellaneousData");
			res = this.createEditModelAndView(pd, "error.miscellaneousData");
		}

		return res;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final MiscellaneousData ed, final BindingResult binding) {
		ModelAndView res;

		try {
			String redirect = "curricula/hacker/show.do?curriculaId=";
			final MiscellaneousData ed1 = this.miscellaneousDataService.findOne(ed.getId());
			redirect = redirect + String.valueOf(ed1.getCurricula().getId());

			this.miscellaneousDataService.delete(ed1);

			res = new ModelAndView("redirect:../../" + redirect);
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(ed, "error.miscellaneousData");
		}

		return res;
	}

}
