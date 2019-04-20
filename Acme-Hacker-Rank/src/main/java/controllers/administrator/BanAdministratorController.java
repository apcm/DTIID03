
package controllers.administrator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import controllers.AbstractController;
import domain.Actor;

@Controller
@RequestMapping("/ban/administrator")
public class BanAdministratorController extends AbstractController {

	@Autowired
	ActorService	actorService;


	//list
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;

		final List<Actor> lActorNotBanned = this.actorService.findAllNotBanned();
		final List<Actor> lActorBanned = this.actorService.findAllBanned();

		result = new ModelAndView("ban/list");

		result.addObject("lH", lActorNotBanned);
		result.addObject("lH2", lActorBanned);
		result.addObject("requestURI", "ban/administrator/list.do");

		return result;
	}

	//	@RequestMapping(value = "/show", method = RequestMethod.GET)
	//	public ModelAndView show(@RequestParam final int actorId) {
	//		final ModelAndView res;
	//		final Actor a;
	//
	//		a = this.actorService.findOne(actorId);
	//
	//		res = new ModelAndView("ban/show");
	//		res.addObject("actor", a);
	//
	//		return res;
	//	}

	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(@RequestParam final int actorId) {
		ModelAndView res;
		final Actor a = this.actorService.findOne(actorId);
		this.actorService.banActor(a);

		res = new ModelAndView("redirect:list.do");

		return res;
	}

	@RequestMapping(value = "/unban", method = RequestMethod.GET)
	public ModelAndView unban(@RequestParam final int actorId) {
		ModelAndView res;
		final Actor a = this.actorService.findOne(actorId);
		this.actorService.unbanActor(a);

		res = new ModelAndView("redirect:list.do");

		return res;
	}
}
