
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

import services.EducationDataService;
import domain.EducationData;

@Controller
@RequestMapping("/educationData/hacker")
public class EducationDataHackerController {

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
	public ModelAndView save(EducationData ed, final BindingResult binding) {
		ModelAndView res;

		try {
			//			if (ed.getDegree() != "" && ed.getInstitution() != "" && (ed.getMark() != null || ed.getMark() < 10 && ed.getMark() > 0) && (ed.getStartMoment() != null || ed.getStartMoment().before(Calendar.getInstance().getTime()))
			//				&& (ed.getEndMoment() != null || ed.getEndMoment().before(Calendar.getInstance().getTime())))
			//Compruebo que no me lleguen las fechas mal
			if (ed.getStartMoment() != null && ed.getEndMoment() != null)
				if (ed.getStartMoment().after(ed.getEndMoment()))
					return res = this.createEditModelAndView(ed, "educationData.date.error");

			//Compruebo que no se intente editar una copia
			if (ed.getId() != 0)
				Assert.isTrue(this.educationDataService.findOne(ed.getId()).getCurricula().getIsCopy() == false, "educationDataCopy.error");

			ed = this.educationDataService.reconstruct(ed, binding);
			this.educationDataService.save(ed);

			String redirect = "curricula/hacker/show.do?curriculaId=";
			redirect = redirect + String.valueOf(ed.getCurricula().getId());
			res = new ModelAndView("redirect:../../" + redirect);
		} catch (final ValidationException oops) {
			res = this.createEditModelAndView(ed);
		} catch (final Throwable oops) {
			if (oops.getMessage() == "educationDataCopy.error")
				res = this.createEditModelAndView(ed, "educationData.copy.error");
			else
				res = this.createEditModelAndView(ed, "error.educationData");
		}

		return res;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final EducationData ed, final BindingResult binding) {
		ModelAndView res;

		try {
			String redirect = "curricula/hacker/show.do?curriculaId=";
			final EducationData ed1 = this.educationDataService.findOne(ed.getId());
			redirect = redirect + String.valueOf(ed1.getCurricula().getId());
			this.educationDataService.delete(ed1);

			res = new ModelAndView("redirect:../../" + redirect);
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(ed, "error.educationData");
		}

		return res;
	}

}
