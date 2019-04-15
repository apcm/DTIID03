package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Position;

import services.PositionService;

@Controller
@RequestMapping("/position/company")
public class PositionCompanyController {
	
	@Autowired
	private PositionService	positionService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		Collection<Position> positions = this.positionService.getMyPositionList();

		result = new ModelAndView("position/company/list");
		result.addObject("positions", positions);
		result.addObject("requestURI", "/position/company/list.do");

		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Position position;

		position = this.positionService.create();

		result = this.createEditModelAndView(position);
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int positionId) {
		ModelAndView result;
		Position position;

		position = this.positionService.findOne(positionId);

		Collection<Problem> problems = this.positionService.getProblems(position);
		if(position.isFinalMode()){
			result = this.list();
		}else{
		result = this.createEditModelAndView(position);
		result.addObject("problems", problems);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Position position, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(position);
		else
			try {
				this.positionService.save(position);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(position, "position.commit.error");
			}
			
		return result;
	}
	
	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancelPosition(@RequestParam final int positionId) {
		ModelAndView result;

		Position p = this.positionService.findOne(positionId);
		
		if(!p.isFinalMode()){
			result = this.list();
		}else{
			this.positionService.cancelP(positionId);
			result = this.list();
		}
			
			
		return result;
	}
	
	

	protected ModelAndView createEditModelAndView(Position position) {
		ModelAndView result;
		result = this.createEditModelAndView(position, null);

		return result;
	}
	
	protected ModelAndView createEditModelAndView(Position position, String messageCode) {
		ModelAndView result;
		
		result = new ModelAndView("position/company/edit");
		result.addObject("position", position);

		result.addObject("message", messageCode);

		return result;
	}
	

	//-------------------------DISPLAY-----------------------------------
	
		@RequestMapping(value = "/display", method = RequestMethod.GET)
		public ModelAndView display(@RequestParam final int positionId) {
			ModelAndView result;
			Position position;

			position = this.positionService.findOne(positionId);
			Assert.notNull(position);

			result = this.createDisplayModelAndView(position);

			return result;
		}

		protected ModelAndView createDisplayModelAndView(final Position position) {
			ModelAndView result;
			result = this.createDisplayModelAndView(position, null);

			return result;
		}

		protected ModelAndView createDisplayModelAndView(final Position position, final String messageCode) {
			ModelAndView result;

			result = new ModelAndView("position/company/display");
			result.addObject("position", position);
			result.addObject("messageCode", messageCode);
		
			return result;

		}


}
