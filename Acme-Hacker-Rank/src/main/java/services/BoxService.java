
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import repositories.BoxRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Box;
import domain.Message;

@Service
@Transactional
public class BoxService {

	@Autowired
	private BoxRepository	messageBoxRepository;

	@Autowired
	private ActorRepository	actorRepository;

	@Autowired
	private MessageService	ms;


	public Box create() {

		final Box msgbox = new Box();

		msgbox.setPredefined(false);
		return msgbox;
	}

	public Collection<Message> messagesByMessageBoxName(final String boxName) {
		final UserAccount actual = LoginService.getPrincipal();
		final Actor a = this.actorRepository.getActor(actual);
		final Collection<Box> mBoxes = a.getBoxes();
		final Collection<Message> res = new ArrayList<>();

		for (final Box mbox : mBoxes)
			if (mbox.getName().equals(boxName))
				for (final Message msg : mbox.getMessages())
					res.add(msg);
		return res;
	}

	public Collection<Box> findAll() {
		return this.messageBoxRepository.findAll();
	}

	public Box findOne(final int messageBoxId) {
		return this.messageBoxRepository.findOne(messageBoxId);
	}

	public Box save(final Box messageBox) {
		final UserAccount actual = LoginService.getPrincipal();
		final Actor a = this.actorRepository.getActor(actual);
		final Box mb = this.messageBoxRepository.save(messageBox);
		if (!a.getBoxes().contains(messageBox)) {
			final Collection<Box> mboxes = a.getBoxes();
			mboxes.add(mb);
			a.setBoxes(mboxes);
			Assert.isTrue(a.getBoxes().contains(mb));

		}
		return mb;

	}

	public Box saveToRemote(final Box messageBox, final Administrator c) {

		final Box mb = this.messageBoxRepository.save(messageBox);
		if (!c.getBoxes().contains(messageBox)) {
			final Collection<Box> mboxes = c.getBoxes();
			mboxes.add(mb);
			c.setBoxes(mboxes);
			Assert.isTrue(c.getBoxes().contains(mb));

		}
		return mb;

	}

	public void delete(final Box messageBox) {
		Assert.isTrue(!messageBox.getPredefined() == true);
		this.messageBoxRepository.delete(messageBox);
	}

	public Message sendMessage(final Message msg) {
		final UserAccount actual = LoginService.getPrincipal();
		final Actor a = this.actorRepository.getActor(actual);
		Assert.notNull(msg);
		Assert.isTrue(!a.getIsBanned());

		final Message result = this.ms.save(msg);

		final Collection<Box> aboxes = a.getBoxes();
		for (final Box abox : aboxes)
			if (abox.getName().endsWith("out box") && abox.getPredefined() == true) {
				final Collection<Message> ames = abox.getMessages();
				ames.add(result);
				abox.setMessages(ames);

			}

		final Collection<Actor> recipients = result.getRecipients();
		for (final Actor r : recipients) {
			final Collection<Box> rboxes = r.getBoxes();
			for (final Box rbox : rboxes)

				if (rbox.getName().endsWith("in box") && rbox.getPredefined() == true) {
					final Collection<Message> rmes = rbox.getMessages();
					rmes.add(result);
					rbox.setMessages(rmes);

				}
		}
		System.out.println();
		System.out.println("Lo guarda");
		return result;
	}

	public Message sendSpamMessage(final Message msg) {
		final UserAccount actual = LoginService.getPrincipal();
		final Actor a = this.actorRepository.getActor(actual);
		Assert.notNull(msg);
		Assert.isTrue(!a.getIsBanned());
		Assert.isTrue(msg.getFlagSpam());

		final Message result = this.ms.save(msg);

		final Collection<Box> aboxes = a.getBoxes();
		for (final Box abox : aboxes)
			if (abox.getName().endsWith("out box") && abox.getPredefined() == true) {
				final Collection<Message> ames = abox.getMessages();
				ames.add(result);
				abox.setMessages(ames);

			}

		final Collection<Actor> recipients = result.getRecipients();
		for (final Actor r : recipients) {
			final Collection<Box> rboxes = r.getBoxes();
			for (final Box rbox : rboxes)

				if (rbox.getName().endsWith("spam box") && rbox.getPredefined() == true) {
					final Collection<Message> rmes = rbox.getMessages();
					rmes.add(result);
					rbox.setMessages(rmes);

				}
		}
		System.out.println();
		System.out.println("Lo guarda");
		return result;
	}

}
