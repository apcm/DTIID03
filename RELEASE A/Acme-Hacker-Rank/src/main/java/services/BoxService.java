
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
import domain.Company;
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

	public Box saveInitial(final Box messageBox) {
		final Box mb = this.messageBoxRepository.save(messageBox);
		this.messageBoxRepository.flush();
		return mb;

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

	public Box saveToRemote(final Box messageBox, final Actor actor) {

		final Box mb = this.messageBoxRepository.save(messageBox);
		if (!actor.getBoxes().contains(messageBox)) {
			final Collection<Box> mboxes = actor.getBoxes();
			mboxes.add(mb);
			actor.setBoxes(mboxes);
			Assert.isTrue(actor.getBoxes().contains(mb));

		}
		return mb;
	}

	public Box saveToRemote(final Box messageBox, final Company c) {

		final Box mb = this.messageBoxRepository.save(messageBox);
		if (!c.getBoxes().contains(messageBox)) {
			final Collection<Box> mboxes = c.getBoxes();
			mboxes.add(mb);
			c.setBoxes(mboxes);
			Assert.isTrue(c.getBoxes().contains(mb));

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
		this.messageBoxRepository.delete(messageBox);
	}

	public Message sendMessage(final Message msg) {
		final UserAccount actual = LoginService.getPrincipal();
		final Actor a = this.actorRepository.getActor(actual);
		Assert.notNull(msg);
		Assert.isTrue(!a.getIsBanned());

		final Message result = this.ms.save(msg);

		final Collection<Box> aboxes = a.getBoxes();
		for (final Box abox : aboxes) {
			final Collection<Message> ames = abox.getMessages();
			ames.add(result);
			abox.setMessages(ames);

		}

		final Actor r = result.getRecipient();
		if (r.getId() != a.getId()) {
			final Collection<Box> rboxes = r.getBoxes();
			for (final Box rbox : rboxes) {

				final Collection<Message> rmes = rbox.getMessages();
				rmes.add(result);
				rbox.setMessages(rmes);

			}
		}
		return result;
	}

	public Message sendAutomaticMessage(final Message msg) {
		final Actor a = msg.getSender();
		Assert.notNull(msg);
		Assert.isTrue(!a.getIsBanned());

		final Message result = this.ms.save(msg);

		final Collection<Box> aboxes = a.getBoxes();
		for (final Box abox : aboxes) {
			final Collection<Message> ames = abox.getMessages();
			ames.add(result);
			abox.setMessages(ames);

		}

		final Actor r = result.getRecipient();
		if (r.getId() != a.getId()) {
			final Collection<Box> rboxes = r.getBoxes();
			for (final Box rbox : rboxes) {

				final Collection<Message> rmes = rbox.getMessages();
				rmes.add(result);
				rbox.setMessages(rmes);

			}
		}
		return result;
	}

}
