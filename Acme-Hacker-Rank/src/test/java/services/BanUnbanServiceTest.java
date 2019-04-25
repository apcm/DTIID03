
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Hacker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class BanUnbanServiceTest extends AbstractTest {

	//SUT
	@Autowired
	private ActorService	actorService;

	@Autowired
	private HackerService	hackerService;


	/**
	 * TESTING REQUIREMENT #24.3 (Ban actor)
	 * COVERED INSTRUCTIONS IN THIS TEST: 100%
	 * COVERED INSTRUCTIONS IN ActorService: 9.5%
	 * COVERED DATA IN THIS TEST: 12%
	 * */

	@Test
	public void testBan() {
		this.authenticate("admin");
		final Hacker h1 = this.hackerService.findOne(this.getEntityId("hacker1"));
		h1.setFlagSpam(true);

		final Hacker h2 = this.hackerService.findOne(this.getEntityId("hacker2"));
		h2.setFlagSpam(false);

		this.unauthenticate();

		final Object testingData[][] = {

			/**
			 * TESTING REQUIREMENT #24.3
			 * POSITIVE TEST
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */
			{
				"admin", h1, null
			},

			/**
			 * // * TESTING REQUIREMENT #24.3
			 * // * NEGATIVE TEST:You can not ban someone which flagSpam is false
			 * (Expected IllegalArgumentException)
			 * // * COVERED INSTRUCTIONS: 100%
			 * // * COVERED DATA: 10%
			 * // *
			 */

			{
				"admin", h2, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateBan((String) testingData[i][0], (Actor) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * TESTING REQUIREMENT #24.4 (UnBan actor)
	 * COVERED INSTRUCTIONS IN THIS TEST: 100%
	 * COVERED INSTRUCTIONS IN ActorService: 9.5%
	 * COVERED DATA IN THIS TEST: 12%
	 * */

	@Test
	public void testUnBan() {
		this.authenticate("admin");
		final Hacker h1 = this.hackerService.findOne(this.getEntityId("hacker1"));
		h1.setFlagSpam(true);
		h1.setIsBanned(true);

		final Hacker h2 = this.hackerService.findOne(this.getEntityId("hacker2"));
		h2.setFlagSpam(true);
		h2.setIsBanned(true);

		this.unauthenticate();

		final Object testingData[][] = {

			/**
			 * TESTING REQUIREMENT #24.4
			 * POSITIVE TEST
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */
			{
				"admin", h1, null
			},

			/**
			 * // * TESTING REQUIREMENT #24.4
			 * // * NEGATIVE TEST:YOU CANNOT UNBAN IF YOU ARE NOT AN ADMIN
			 * (Expected IllegalArgumentException)
			 * // * COVERED INSTRUCTIONS: 100%
			 * // * COVERED DATA: 10%
			 * // *
			 */

			{
				"hacker1", h2, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateBan((String) testingData[i][0], (Actor) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateBan(final String username, final Actor a, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);
			this.actorService.banActor(a);
			final Actor a1 = this.actorService.save(a);
			Assert.isTrue(a1.getIsBanned() == true);
			this.actorService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	protected void templateUnBan(final String username, final Actor a, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);
			this.actorService.unbanActor(a);
			final Actor a1 = this.actorService.save(a);
			Assert.isTrue(a1.getIsBanned() == false);
			this.actorService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}
}
