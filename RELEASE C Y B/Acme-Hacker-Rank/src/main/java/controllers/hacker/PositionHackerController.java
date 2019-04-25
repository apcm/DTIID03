
package controllers.hacker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.HackerService;
import services.PositionService;
import domain.Application;
import domain.Hacker;
import domain.Position;

@Controller
@RequestMapping("/position/hacker")
public class PositionHackerController {

	@Autowired
	private PositionService		positionService;

	@Autowired
	private HackerService		hackerService;

	@Autowired
	private ApplicationService	applicationService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		//Listamos las positions en finalmode y no canceladas
		final Collection<Position> positions = this.positionService.findPositionFinalModeNotCancelled();

		//Nos quedamos solo con aquellas para las que no ha enviado una aplicación ya
		final Hacker logged = this.hackerService.findByPrincipal();
		final Collection<Application> applications = this.applicationService.getApplicationsByHacker(logged);
		final List<Position> positionsHacker = new ArrayList<>();
		for (final Application a : applications)
			positionsHacker.add(a.getPosition());
		positions.removeAll(positionsHacker);

		result = new ModelAndView("position/list");
		result.addObject("positions", positions);
		result.addObject("requestURI", "/position/hacker/list.do");
		final boolean showError = false;
		result.addObject("showError", showError);

		return result;
	}
}
