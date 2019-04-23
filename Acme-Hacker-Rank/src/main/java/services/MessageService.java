
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
import domain.Actor;
import domain.Box;
import domain.Message;

@Service
@Transactional
public class MessageService {

	@Autowired
	private MessageRepository	messageRepository;

	@Autowired
	private ActorRepository		ar;

	@Autowired
	private ActorService		as;

	@Autowired
	private BoxService			mbs;


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
		Assert.isTrue(!actorActual.getIsBanned());

		final List<Box> msgb = (List<Box>) actorActual.getBoxes();
		final Box pull = msgb.get(0);
		if (!m.getTag().contains("DELETED")) {
			for (final Message mens : pull.getMessages())
				if (mens.getId() == m.getId())
					mens.setTag(mens.getTag() + ", DELETED");
		} else {
			Message toDelete = null;
			final Collection<Message> newCol = pull.getMessages();
			for (final Message mens : pull.getMessages())
				//Actor actual
				if (mens.getId() == m.getId())
					toDelete = mens;

			//El otro actor (sender o recipient)
			if (actorActual.getId() == m.getSender().getId()) {
				final List<Box> aux2 = (List<Box>) m.getRecipient().getBoxes();
				final Box newCol2 = aux2.get(0);
				for (final Message mens2 : newCol2.getMessages())
					//Actor actual
					if (mens2.getId() == m.getId())
						toDelete = mens2;

				newCol2.getMessages().remove(toDelete);
				aux2.get(0).setMessages(newCol2.getMessages());
				this.mbs.saveToRemote(aux2.get(0), m.getRecipient());
			} else {
				final List<Box> aux2 = (List<Box>) m.getSender().getBoxes();
				final Box newCol2 = aux2.get(0);
				for (final Message mens2 : newCol2.getMessages())
					//Actor actual
					if (mens2.getId() == m.getId())
						toDelete = mens2;

				newCol2.getMessages().remove(toDelete);
				aux2.get(0).setMessages(newCol2.getMessages());
				this.mbs.saveToRemote(aux2.get(0), m.getSender());
			}
			newCol.remove(toDelete);
			pull.setMessages(newCol);
			this.mbs.save(pull);
			this.delete(m);
		}

	}

	public void broadcastMessage(final Message msg) {

		final UserAccount actual = LoginService.getPrincipal();
		final Actor admin = this.ar.getActor(actual);
		Assert.notNull(msg);
		Assert.isTrue(!admin.getIsBanned());

		final Collection<Actor> listaActores = this.as.findAll();
		listaActores.remove(admin);
		for (final Actor actors : listaActores) {
			msg.setRecipient(actors);
			final Message result = this.save(msg);
			for (final Box msb : actors.getBoxes())

				if (msb.getName().endsWith("in box") && msb.getPredefined() == true) {
					final Collection<Message> rmes = msb.getMessages();
					rmes.add(result);
					msb.setMessages(rmes);
				}
			for (final Box msb : admin.getBoxes())

				if (msb.getName().endsWith("in box") && msb.getPredefined() == true) {
					final Collection<Message> rmes = msb.getMessages();
					rmes.add(result);
					msb.setMessages(rmes);
				}

		}
	}

	public Message broadcastSpamMessage(final Message msg) {
		final UserAccount actual = LoginService.getPrincipal();
		final Actor admin = this.ar.getActor(actual);
		Assert.notNull(msg);
		Assert.isTrue(!admin.getIsBanned());

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
