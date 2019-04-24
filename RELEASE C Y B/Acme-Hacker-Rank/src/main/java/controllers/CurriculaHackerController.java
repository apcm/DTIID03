
package controllers;

import java.util.List;

import javax.validation.ValidationException;

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
import services.MiscellaneousDataService;
import services.PersonalDataService;
import services.PositionDataService;
import domain.Curricula;
import domain.EducationData;
import domain.MiscellaneousData;
import domain.PersonalData;
import domain.PositionData;

@Controller
@RequestMapping("/curricula/hacker")
public class CurriculaHackerController extends AbstractController {

	@Autowired
	HackerService				hackerService;

	@Autowired
	CurriculaService			curriculaService;

	@Autowired
	PositionDataService			positionDataService;

	@Autowired
	MiscellaneousDataService	miscellaneousDataService;

	@Autowired
	PersonalDataService			personaldataService;

	@Autowired
	EducationDataService		educationDataService;


	CurriculaHackerController() {

	}

	//list
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;

		final List<Curricula> cList = this.curriculaService.getCurriculasFromHacker();

		result = new ModelAndView("curricula/list");

		result.addObject("cList", cList);
		result.addObject("requestURI", "curricula/hacker/list.do");

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int curriculaId) {
		final ModelAndView res;
		final Curricula c;
		final List<EducationData> ed;
		final List<PersonalData> pd;
		final List<PositionData> pd2;
		final List<MiscellaneousData> md;

		c = this.curriculaService.findOne(curriculaId);
		ed = this.educationDataService.findByCurriculaId(curriculaId);
		pd = this.personaldataService.findByCurriculaId(curriculaId);
		pd2 = this.positionDataService.findByCurriculaId(curriculaId);
		md = this.miscellaneousDataService.findByCurriculaId(curriculaId);

		res = new ModelAndView("curricula/show");
		res.addObject("requestURI", "curricula/hacker/show.do" + "?curriculaId=" + String.valueOf(c.getId()));
		res.addObject("curricula", c);
		res.addObject("educationData", ed);
		res.addObject("personalData", pd);
		res.addObject("positionData", pd2);
		res.addObject("miscellaneousData", md);

		return res;
	}

	//crear
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView res;
		final Curricula c = this.curriculaService.create();

		res = this.createEditModelAndView(c);

		return res;
	}

	private ModelAndView createEditModelAndView(final Curricula c) {
		ModelAndView res;
		res = this.createEditModelAndView(c, null);

		return res;
	}

	private ModelAndView createEditModelAndView(final Curricula c, final String messageCode) {
		ModelAndView res;
		res = new ModelAndView("curricula/edit");

		res.addObject("curricula", c);
		res.addObject("message", messageCode);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView save(@RequestParam final int curriculaId) {
		ModelAndView res;
		final Curricula c = this.curriculaService.findOne(curriculaId);

		res = new ModelAndView("curricula/edit");

		res.addObject("curricula", c);
		Assert.notNull(c);
		res = this.createEditModelAndView(c);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Curricula c, final BindingResult binding) {
		ModelAndView res;

		try {
			if (c.getId() != 0)
				Assert.isTrue(!this.curriculaService.findOne(c.getId()).getIsCopy(), "curriculaCopy");
			c = this.curriculaService.reconstruct(c, binding);
			this.curriculaService.save(c);

			res = new ModelAndView("redirect:list.do");
		} catch (final ValidationException oops) {
			res = this.createEditModelAndView(c);
		} catch (final Throwable oops) {
			if (oops.getMessage() == "curriculaCopy")
				res = this.createEditModelAndView(c, "curricula.copy.error");

			else
				res = this.createEditModelAndView(c, "error.curricula");
		}

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Curricula c, final BindingResult binding) {
		ModelAndView res;

		try {
			final Curricula c1 = this.curriculaService.findOne(c.getId());
			this.curriculaService.delete(c1);
			res = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(c, "error.curricula");
		}

		return res;
	}
}
