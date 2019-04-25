
package controllers;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PositionService;
import domain.Position;

@Controller
@RequestMapping("/search")
public class SearchController extends AbstractController {

	@Autowired
	PositionService	positionService;


	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView searchFormAndResults(final Model model) {
		final ModelAndView res = new ModelAndView("search/search");
		return res;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET, params = "search")
	public ModelAndView search(@RequestParam("keyword") final String keyword, final HttpServletRequest request, final HttpServletResponse response) {
		final ModelAndView res = new ModelAndView("search/search");
		final Collection<Position> positions = this.positionService.searchPositions(keyword);
		res.addObject("positions", positions);
		return res;
	}
}
