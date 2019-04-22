
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

import services.CompanyService;
import services.ProblemService;
import domain.Company;
import domain.Problem;

@Controller
@RequestMapping("/problem/company")
public class ProblemCompanyController extends AbstractController {

	@Autowired
	ProblemService	problemService;

	@Autowired
	CompanyService	companyService;


	public ProblemCompanyController() {

	}

	//list
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;

		final Company c = this.companyService.findOnePrincipal();
		final List<Problem> pList = this.problemService.getAllProblemsByCompanyId(c.getId());

		result = new ModelAndView("problem/list");

		result.addObject("pList", pList);
		result.addObject("requestURI", "problem/company/list.do");

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int problemId) {
		final ModelAndView res;
		final Problem p;

		p = this.problemService.findOne(problemId);

		res = new ModelAndView("problem/show");
		res.addObject("problem", p);

		return res;
	}

	//crear
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView res;
		final Problem p = this.problemService.create();

		res = this.createEditModelAndView(p);

		return res;
	}

	private ModelAndView createEditModelAndView(final Problem p) {
		ModelAndView res;
		res = this.createEditModelAndView(p, null);

		return res;
	}

	private ModelAndView createEditModelAndView(final Problem p, final String messageCode) {
		ModelAndView res;
		res = new ModelAndView("problem/edit");

		res.addObject("problem", p);
		res.addObject("message", messageCode);

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView save(@RequestParam final int problemId) {
		ModelAndView res;
		final Problem p = this.problemService.findOne(problemId);

		res = new ModelAndView("problem/edit");

		res.addObject("problem", p);
		Assert.notNull(p);
		res = this.createEditModelAndView(p);

		return res;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Problem p, final BindingResult binding) {
		ModelAndView res;

		try {
			if (p.getId() != 0)
				Assert.isTrue(this.problemService.findOne(p.getId()).isFinalMode() != true, "finalMode");
			p = this.problemService.reconstruct(p, binding);
			this.problemService.save(p);

			res = new ModelAndView("redirect:list.do");
		} catch (final ValidationException oops) {
			res = this.createEditModelAndView(p);
		} catch (final Throwable oops) {

			if (oops.getMessage() == "finalMode")
				res = this.createEditModelAndView(p, "problem.finalMode.error");

			else
				res = this.createEditModelAndView(p, "error.problem");
		}

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Problem p, final BindingResult binding) {
		ModelAndView res;

		try {
			final Problem p1 = this.problemService.findOne(p.getId());
			this.problemService.deleteProblem(p1);
			res = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(p, "error.problem");
		}

		return res;
	}
}
