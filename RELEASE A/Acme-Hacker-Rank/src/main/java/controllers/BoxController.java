
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import domain.Box;

@Controller
@RequestMapping("/boxes")
public class BoxController extends AbstractController {

	@Autowired
	private ActorService	as;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		final Collection<Box> mBox;

		mBox = this.as.getMyBoxes();

		result = new ModelAndView("messageBox/list");

		result.addObject("messageBoxes", mBox);

		return result;

	}
}
