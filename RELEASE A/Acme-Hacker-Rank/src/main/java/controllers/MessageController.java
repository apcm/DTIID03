
package controllers;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import repositories.ActorRepository;
import repositories.MessageRepository;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.BoxService;
import services.MessageService;
import domain.Actor;
import domain.Box;
import domain.Message;

@Controller
@RequestMapping("/messages")
public class MessageController extends AbstractController {

	@Autowired
	private MessageService		ms;

	@Autowired
	private MessageRepository	mr;

	@Autowired
	private ActorService		as;

	@Autowired
	private BoxService			mbs;

	@Autowired
	private ActorRepository		ar;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int boxId) {

		ModelAndView result;
		final Collection<Message> mes;

		final UserAccount actual = LoginService.getPrincipal();
		final Actor a = this.ar.getActor(actual);
		final Box listing = this.mbs.findOne(boxId);

		Assert.isTrue(a.getBoxes().contains(listing));

		mes = this.mbs.messagesByMessageBoxName(listing.getName());
		result = new ModelAndView("messages/list");

		result.addObject("messages", mes);

		result.addObject("requestURI", "/messages/list.do");

		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Message message;

		message = this.ms.create();

		result = this.createEditModelAndView(message);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Message m) {
		ModelAndView result;
		result = this.createEditModelAndView(m, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Message m, final String messageCode) {
		ModelAndView result;
		Collection<Actor> recipients;

		recipients = this.as.findAll();

		result = new ModelAndView("messages/edit");
		result.addObject("mesInformation", m);
		result.addObject("recipient", recipients);
		result.addObject("mesError", messageCode);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "send")
	public ModelAndView send(@Valid final Message message, final BindingResult binding) {
		ModelAndView result;

		final UserAccount actual = LoginService.getPrincipal();
		final Actor a = this.ar.getActor(actual);
		//PARA EVITAR GET HACKING:
		message.setSender(a);

		if (binding.hasErrors())
			result = this.createEditModelAndView(message);
		else
			try {

				final List<Box> mbox = (List<Box>) a.getBoxes();
				final Integer id = mbox.get(0).getId();

				this.mbs.sendMessage(message);

				result = new ModelAndView("redirect:list.do?boxId=" + id);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(message, "messages.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/breach", method = RequestMethod.GET)
	public ModelAndView breach() {
		ModelAndView result;
		Message message;

		message = this.ms.create();
		final String messageText = this.ms.getTemplateSecurityBreachNotificationMessage();
		message.setBody(messageText);
		message.setSubject("Security breach on the system");
		message.setTag("SYSTEM, ");

		result = this.createEditModelAndView(message);
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int messageId) {
		ModelAndView result;
		Message message;

		message = this.ms.findOne(messageId);
		final UserAccount actual = LoginService.getPrincipal();
		final Actor a = this.ar.getActor(actual);

		final Actor recipient = message.getRecipient();

		try {
			Assert.notNull(message);
			Assert.notNull(message);
			Assert.isTrue(recipient.equals(a) || message.getSender().getId() == a.getId());

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/boxes/list.do");
			return result;
		}

		result = this.createDisplayModelAndView(message);

		return result;
	}

	protected ModelAndView createDisplayModelAndView(final Message m) {
		ModelAndView result;
		result = this.createDisplayModelAndView(m, null);

		return result;
	}

	protected ModelAndView createDisplayModelAndView(final Message m, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("messages/display");
		result.addObject("messageInfo", m);
		result.addObject("messageCode", messageCode);

		return result;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int messageId) {
		ModelAndView result;
		final Message message = this.ms.findOne(messageId);
		final Actor actual = this.as.findByPrincipal();
		//TODO: Comprobar que el mensaje le pertenece.
		try {
			Assert.isTrue(message.getSender().getId() == actual.getId() || message.getRecipient().getId() == actual.getId());
			this.ms.deleteMessage(message);
			result = new ModelAndView("redirect:/boxes/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/boxes/list.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "sendToAll")
	public ModelAndView broadcast(@Valid final Message message, final BindingResult binding) {
		ModelAndView result;

		final UserAccount actual = LoginService.getPrincipal();
		final Actor a = this.ar.getActor(actual);

		if (binding.hasErrors())
			result = this.createEditModelAndView(message);
		else
			try {
				if (!message.getTag().contains("SYSTEM"))
					message.setTag(message.getTag() + ", SYSTEM");
				this.ms.broadcastMessage(message);
				final List<Box> mbox = (List<Box>) a.getBoxes();
				final Integer id = mbox.get(0).getId();
				result = new ModelAndView("redirect:list.do?boxId=" + id);

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(message, "messages.commit.error");
			}

		return result;

	}
}
