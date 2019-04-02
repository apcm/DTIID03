
package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import repositories.MessageRepository;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Actor;
import domain.Box;
import domain.Message;

@Service
@Transactional
public class MessageService {

	@Autowired
	private MessageRepository		messageRepository;

	@Autowired
	private ActorRepository			ar;

	@Autowired
	private UserAccountRepository	ur;

	@Autowired
	private ActorService			as;

	@Autowired
	private BoxService				mbs;


	public Message create() {
		final Date date = new Date();
		final Message m = new Message();
		final UserAccount actual = LoginService.getPrincipal();
		final Actor a = this.ar.getActor(actual);
		m.setMoment(date);
		m.setSender(a);
		System.out.println("create servicio:");
		System.out.println(m.getSender());
		return m;
	}

	public void deleteMessage(final Message m) {
		Assert.notNull(m);
		Assert.isTrue(!(m.getId() == 0));
		final UserAccount actual = LoginService.getPrincipal();
		final Actor actorActual = this.ar.getActor(actual);
		Assert.isTrue(!actorActual.isBanned());

		final List<Box> msgb = (List<Box>) actorActual.getBoxes();
		final Box trash = msgb.get(3);
		Boolean addToTrash = false;
		if (trash.getMessages().contains(m)) {
			final Collection<Message> mess = trash.getMessages();
			mess.remove(m);
			trash.setMessages(mess);
			this.mbs.save(trash);
			addToTrash = true;

			Boolean bBorrar = true;
			final Collection<Actor> actores = this.as.findAll();
			actores.remove(actorActual);
			for (final Actor a : actores) {
				for (final Box mboxes : a.getBoxes()) {
					for (final Message mes : mboxes.getMessages())
						if (mes.getId() == m.getId()) {
							bBorrar = false;
							break;
						}

					if (!bBorrar)
						break;
				}
				if (!bBorrar)
					break;
			}
			if (bBorrar) {
				this.delete(m);
				return;
			}
		}
		for (int i = 0; i < msgb.size(); i++)
			if (msgb.get(i).getMessages().contains(m)) {
				final Box boxm = msgb.get(i);

				if (!boxm.getName().endsWith("trash box")) {
					final Collection<Message> mthere = boxm.getMessages();
					mthere.remove(m);
					boxm.setMessages(mthere);
					this.mbs.save(boxm);

					if (addToTrash == false) {
						final Box trashDestino = msgb.get(3);
						final Collection<Message> tmessages = trashDestino.getMessages();
						tmessages.add(m);
						trashDestino.setMessages(tmessages);
						this.mbs.save(trashDestino);
					}
				}
			}
	}
	private void storeMessageOnTrashBox(final Message m, final Actor a) {
		Assert.notNull(a);
		Assert.notNull(m);
		final Collection<Box> msgboxes = a.getBoxes();

		for (final Box mbox : msgboxes)
			if (mbox.getPredefined() == true && mbox.getName().endsWith("trash box")) {
				final Collection<Message> messages = mbox.getMessages();
				messages.add(m);
				mbox.setMessages(messages);
				Assert.notNull(mbox);

			}
	}

	public Message broadcastMessage(final Message msg) {
		final UserAccount actual = LoginService.getPrincipal();
		final Actor admin = this.ar.getActor(actual);
		Assert.notNull(msg);
		Assert.isTrue(!admin.isBanned());

		final Message result = this.save(msg);

		final Collection<Box> aboxes = admin.getBoxes();
		for (final Box abox : aboxes)
			if (abox.getName().endsWith("out box") && abox.getPredefined() == true) {
				final Collection<Message> ames = abox.getMessages();
				ames.add(result);
				abox.setMessages(ames);
			}

		final Collection<Actor> listaActores = this.as.findAll();
		listaActores.remove(admin);
		for (final Actor actors : listaActores)
			for (final Box msb : actors.getBoxes())

				if (msb.getName().endsWith("notification box") && msb.getPredefined() == true) {
					final Collection<Message> rmes = msb.getMessages();
					rmes.add(result);
					msb.setMessages(rmes);
				}
		return result;

	}

	public Message broadcastSpamMessage(final Message msg) {
		final UserAccount actual = LoginService.getPrincipal();
		final Actor admin = this.ar.getActor(actual);
		Assert.notNull(msg);
		Assert.isTrue(!admin.isBanned());

		final Message result = this.save(msg);

		final Collection<Box> aboxes = admin.getBoxes();
		for (final Box abox : aboxes)
			if (abox.getName().endsWith("out box") && abox.getPredefined() == true) {
				final Collection<Message> ames = abox.getMessages();
				ames.add(result);
				abox.setMessages(ames);
			}

		final Collection<Actor> listaActores = this.as.findAll();
		listaActores.remove(admin);
		for (final Actor actors : listaActores)
			for (final Box msb : actors.getBoxes())

				if (msb.getName().endsWith("spam box") && msb.getPredefined() == true) {
					final Collection<Message> rmes = msb.getMessages();
					rmes.add(result);
					msb.setMessages(rmes);
				}
		return result;

	}

	public String getTemplateSecurityBreachNotificationMessage() {
		return "Lamentamos informar de que hemos encontrado una posible brecha de seguridad" + " que podría afectar a los datos e información que usted como usuario ha ingresado"
			+ " en nuestra web. Como consecuencia, sus datos, usuario y contraseña pueden haber sido" + " filtrados a personas ajenas a Acme. Por favor, le pedimos que cambie su contraseña lo antes posible, "
			+ "y compruebe que su información está inalterada. \n \n Si necesita información sobre este asunto, por favor, "
			+ "no dude en contactar con nosotros usando la dirección de correo support.madruga@acme.com o utilizando nuestro teléfono de asistencia al cliente."
			+ " La brecha de seguridad ha sido identificada y estamos trabajando para poder solucionar este problema lo antes posible. \n De nuevo, desde Acme, lamentamos lo sucedido."
			+ " \n \n We are sorry to admit that we found a security breach that can affect the data and information you have introduced in our domain as an user."
			+ "Due to this breach, your data, user and password may be filtered to people alien to Acme. Please, we ask you to change your password as soon as possible, and to check that your information and data are still intact."
			+ "\n\n If you need further information about this issue, please be sure to contact us using the email support.madruga@acme.com or our customer service phone."
			+ "The security breach has been identified and we are working hard to fix it. \n Once again, we are very sorry for this error. ";
	}
	public Collection<Message> findAll() {
		return this.messageRepository.findAll();
	}

	public Message findOne(final int messageId) {
		return this.messageRepository.findOne(messageId);
	}

	public Message save(final Message message) {
		return this.messageRepository.save(message);
	}

	public void delete(final Message message) {
		this.messageRepository.delete(message);
	}

}
