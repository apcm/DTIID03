
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Box;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class BroadCastMessageServiceTest extends AbstractTest {

	//SUT
	@Autowired
	private MessageService	messageService;

	@Autowired
	private HackerService	hackerService;


	/**
	 * TESTING REQUIREMENT #24.1 (Manage education data in a curricula:create)
	 * COVERED INSTRUCTIONS IN THIS TEST: 99.4%
	 * COVERED INSTRUCTIONS IN MessageService: 23.3%
	 * COVERED DATA IN THIS TEST: 12%
	 * */

	@Test
	public void createBroadcast() {
		this.authenticate("admin");
		final Message m = this.messageService.create();
		m.setBody("Body1");
		m.setBroadcast(true);
		m.setMoment(Calendar.getInstance().getTime());
		m.setSubject("Subject");
		m.setTag("Tag1");

		final Message m2 = this.messageService.create();
		m2.setBody("");
		m2.setBroadcast(false);
		m2.setMoment(Calendar.getInstance().getTime());
		m2.setSubject("Subject");
		m2.setTag("Tag1");

		this.unauthenticate();

		final Object testingData[][] = {

			/**
			 * TESTING REQUIREMENT #24.1
			 * POSITIVE TEST
			 * COVERED INSTRUCTIONS: 99.4%
			 * COVERED DATA: 10%
			 * */
			{
				"admin", m, null
			},

			/**
			 * // * TESTING REQUIREMENT #24.1
			 * // * NEGATIVE TEST:YOU CANNOT CREATE A BROADCAST MESSAGE WITHOUT BODY
			 * (Expected ConstraintViolationException)
			 * // * COVERED INSTRUCTIONS: 100%
			 * // * COVERED DATA: 10%
			 * // *
			 */

			{
				"admin", m2, ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.template2((String) testingData[i][0], (Message) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void template2(final String username, final Message m, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);
			final List<Box> lb = new ArrayList<Box>(this.hackerService.findOne(this.getEntityId("hacker1")).getBoxes());
			final Integer i1 = lb.get(0).getMessages().size();
			this.messageService.broadcastMessage(m);
			final List<Box> lb2 = new ArrayList<Box>(this.hackerService.findOne(this.getEntityId("hacker1")).getBoxes());
			final Integer i2 = lb2.get(0).getMessages().size();
			Assert.isTrue(i1 < i2);

			this.messageService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}
}
