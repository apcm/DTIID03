
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
		result.addObject("descendants", this.mbs.findOne(boxId).getDescendants());

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

		if (binding.hasErrors())
			result = this.createEditModelAndView(message);
		else
			try {

				final List<Box> mbox = (List<Box>) a.getBoxes();
				final Integer id = mbox.get(2).getId();

				if (this.as.checkspammer(message.getBody()) == true || this.as.checkspammer(message.getSubject()) == true || this.as.checkspammer(message.getTag()) == true) {
					message.setFlagSpam(true);
					a.setFlagSpam(true);
					this.as.save(a);
					this.mbs.sendSpamMessage(message);
				} else
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

		final Collection<Actor> recipients = this.mr.getRecipientsWorking(messageId);

		try {
			Assert.notNull(message);
			Assert.notNull(message);
			Assert.isTrue(recipients.contains(a) || message.getSender().getId() == a.getId());

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect: list.do");
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
	public ModelAndView edit(@RequestParam final int messageId) {
		ModelAndView result;
		Message message;

		message = this.ms.findOne(messageId);
		final UserAccount actual = LoginService.getPrincipal();
		final Actor a = this.ar.getActor(actual);

		final Collection<Actor> recipients = this.mr.getRecipientsWorking(messageId);

		Assert.notNull(message);
		Assert.isTrue(recipients.contains(a) || message.getSender().getId() == a.getId());

		result = this.createDeleteModelAndView(message);

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Message message, final BindingResult binding) {
		ModelAndView result;

		try {
			this.ms.deleteMessage(message);
			result = new ModelAndView("redirect:/boxes/list.do");
		} catch (final Throwable oops) {
			result = this.createDeleteModelAndView(message, "messages.commit.error");
		}

		return result;
	}

	protected ModelAndView createDeleteModelAndView(final Message m) {
		ModelAndView result;
		result = this.createDeleteModelAndView(m, null);

		return result;
	}

	protected ModelAndView createDeleteModelAndView(final Message m, final String messageCode) {
		ModelAndView result;
		Collection<Actor> recipients;

		recipients = this.as.findAll();

		result = new ModelAndView("messages/delete");
		result.addObject("mesInformation", m);
		result.addObject("recipients", recipients);
		result.addObject("mesError", messageCode);

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
				message.setBroadcast(true);
				message.setRecipients(this.as.findAll());
				if (this.as.checkspammer(message.getBody()) == true || this.as.checkspammer(message.getSubject()) == true || this.as.checkspammer(message.getTag()) == true) {
					message.setFlagSpam(true);
					a.setFlagSpam(true);
					this.as.save(a);
					this.ms.broadcastSpamMessage(message);
				} else
					this.ms.broadcastMessage(message);
				final List<Box> mbox = (List<Box>) a.getBoxes();
				final Integer id = mbox.get(2).getId();
				result = new ModelAndView("redirect:list.do?boxId=" + id);

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(message, "messages.commit.error");
			}

		return result;

	}

	/*
	 * @Autowired
	 * MessageService ms;
	 * 
	 * @Autowired
	 * ActorService as;
	 * 
	 * @Autowired
	 * private SystemConfigService scs;
	 * 
	 * @Autowired
	 * BoxService mbs;
	 * 
	 * @Autowired
	 * AdministratorService ads;
	 * 
	 * @Autowired
	 * ActorRepository ar;
	 * 
	 * 
	 * @RequestMapping(value = "/list", method = RequestMethod.GET)
	 * public ModelAndView list(@RequestParam final int messageBoxId) {
	 * 
	 * ModelAndView result;
	 * final Collection<Message> mes;
	 * 
	 * mes = this.as.messagesByMessageBoxName(this.mbs.findOne(messageBoxId).getName());
	 * result = new ModelAndView("messages/list");
	 * 
	 * System.out.println(mes);
	 * result.addObject("messages", mes);
	 * 
	 * result.addObject("requestURI", "/messages/list.do");
	 * final String banner = this.scs.getSystemConfig().getBanner();
	 * result.addObject("bannerImage", banner);
	 * return result;
	 * 
	 * }
	 * 
	 * @RequestMapping(value = "/create", method = RequestMethod.GET)
	 * public ModelAndView create() {
	 * ModelAndView result;
	 * Message message;
	 * 
	 * message = this.ms.create();
	 * 
	 * result = this.createEditModelAndView(message);
	 * System.out.println("create controlador :");
	 * System.out.println(message.getSender());
	 * return result;
	 * }
	 * 
	 * @RequestMapping(value = "/delete", method = RequestMethod.GET)
	 * public ModelAndView edit(@RequestParam final int messageId) {
	 * ModelAndView result;
	 * Message message;
	 * 
	 * message = this.ms.findOne(messageId);
	 * System.out.println("edit GET");
	 * System.out.println(message.getSender());
	 * System.out.println(message.getRecipients());
	 * Assert.notNull(message);
	 * result = this.createDeleteModelAndView(message);
	 * 
	 * return result;
	 * }
	 * 
	 * @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "send")
	 * public ModelAndView send(@Valid final Message message, final BindingResult binding) {
	 * ModelAndView result;
	 * 
	 * final UserAccount actual = LoginService.getPrincipal();
	 * final Actor a = this.ar.getActor(actual);
	 * 
	 * if (binding.hasErrors()) {
	 * System.out.println("tiene errores");
	 * 
	 * result = this.createEditModelAndView(message);
	 * } else
	 * try {
	 * System.out.println("antes de llegar al servicio");
	 * 
	 * final List<MessageBox> mbox = (List<MessageBox>) a.getMessageBoxes();
	 * final Integer id = mbox.get(1).getId();
	 * 
	 * this.as.sendMessage(message);
	 * System.out.println("LLega al servicio");
	 * result = new ModelAndView("redirect:list.do?messageBoxId=" + id);
	 * } catch (final Throwable oops) {
	 * System.out.println("oops:" + oops);
	 * result = this.createEditModelAndView(message, "messages.commit.error");
	 * }
	 * 
	 * return result;
	 * }
	 * 
	 * @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "sendToAll")
	 * public ModelAndView broadcast(@Valid final Message message, final BindingResult binding) {
	 * ModelAndView result;
	 * 
	 * final UserAccount actual = LoginService.getPrincipal();
	 * final Actor a = this.ar.getActor(actual);
	 * 
	 * if (binding.hasErrors()) {
	 * result = this.createEditModelAndView(message);
	 * System.out.println("el formulario tiene errores");
	 * 
	 * } else
	 * try {
	 * this.ads.broadcastMessage(message);
	 * final List<MessageBox> mbox = (List<MessageBox>) a.getMessageBoxes();
	 * final Integer id = mbox.get(1).getId();
	 * result = new ModelAndView("redirect:list.do?messageBoxId=" + id);
	 * } catch (final Throwable oops) {
	 * result = this.createEditModelAndView(message, "messages.commit.error");
	 * }
	 * 
	 * return result;
	 * }
	 * 
	 * @RequestMapping(value = "/delete", method = RequestMethod.POST, params = "delete")
	 * public ModelAndView delete(final Message message, final BindingResult binding) {
	 * ModelAndView result;
	 * 
	 * try {
	 * this.as.deleteMessage(message);
	 * result = new ModelAndView("redirect:/messageBox/list.do");
	 * } catch (final Throwable oops) {
	 * result = this.createDeleteModelAndView(message, "messages.commit.error");
	 * }
	 * 
	 * return result;
	 * }
	 * 
	 * protected ModelAndView createEditModelAndView(final Message m) {
	 * ModelAndView result;
	 * result = this.createEditModelAndView(m, null);
	 * 
	 * return result;
	 * }
	 * 
	 * protected ModelAndView createEditModelAndView(final Message m, final String messageCode) {
	 * ModelAndView result;
	 * Collection<Actor> recipients;
	 * 
	 * recipients = this.as.findAll();
	 * 
	 * result = new ModelAndView("messages/edit");
	 * result.addObject("mesInformation", m);
	 * result.addObject("recipients", recipients);
	 * System.out.println("llega hasta el editmodelandview");
	 * System.out.println(m.getSender());
	 * result.addObject("mesError", messageCode);
	 * final String banner = this.scs.getSystemConfig().getBanner();
	 * result.addObject("bannerImage", banner);
	 * return result;
	 * }
	 * 
	 * protected ModelAndView createDeleteModelAndView(final Message m) {
	 * ModelAndView result;
	 * result = this.createDeleteModelAndView(m, null);
	 * 
	 * return result;
	 * }
	 * 
	 * protected ModelAndView createDeleteModelAndView(final Message m, final String messageCode) {
	 * ModelAndView result;
	 * Collection<Actor> recipients;
	 * 
	 * recipients = this.as.findAll();
	 * 
	 * result = new ModelAndView("messages/delete");
	 * result.addObject("mesInformation", m);
	 * result.addObject("recipients", recipients);
	 * System.out.println("llega hasta el editmodelandview");
	 * System.out.println(m.getSender());
	 * result.addObject("mesError", messageCode);
	 * final String banner = this.scs.getSystemConfig().getBanner();
	 * result.addObject("bannerImage", banner);
	 * return result;
	 * }
	 * 
	 * @RequestMapping(value = "/display", method = RequestMethod.GET)
	 * public ModelAndView display(@RequestParam final int messageId) {
	 * ModelAndView result;
	 * Message message;
	 * 
	 * message = this.ms.findOne(messageId);
	 * System.out.println(message.getId());
	 * System.out.println(message.getBody());
	 * Assert.notNull(message);
	 * 
	 * result = this.createDisplayModelAndView(message);
	 * 
	 * return result;
	 * }
	 * 
	 * protected ModelAndView createDisplayModelAndView(final Message m) {
	 * ModelAndView result;
	 * result = this.createDisplayModelAndView(m, null);
	 * 
	 * return result;
	 * }
	 * 
	 * protected ModelAndView createDisplayModelAndView(final Message m, final String messageCode) {
	 * ModelAndView result;
	 * System.out.println(m.getBody());
	 * 
	 * result = new ModelAndView("messages/display");
	 * result.addObject("messageInfo", m);
	 * result.addObject("messageCode", messageCode);
	 * final String banner = this.scs.getSystemConfig().getBanner();
	 * result.addObject("bannerImage", banner);
	 * return result;
	 * 
	 * }
	 */
}
