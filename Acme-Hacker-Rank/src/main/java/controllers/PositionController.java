package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Position;

import services.PositionService;

@Controller
@RequestMapping("/position")
public class PositionController extends AbstractController{
	
	@Autowired
	private PositionService positionService;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView res;
		
		Collection<Position> positions = positionService.findAll();
		
		res = new ModelAndView("position/list");
		res = new ModelAndView("float/list");
		res.addObject("requestURI", "position/list.do");
		res.addObject("positions", positions);
		
		return res;
		
	}

}
