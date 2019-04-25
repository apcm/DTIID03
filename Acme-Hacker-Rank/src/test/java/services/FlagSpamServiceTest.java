
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class FlagSpamServiceTest extends AbstractTest {

	//SUT
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private HackerService			hackerService;


	/**
	 * TESTING REQUIREMENT #24.2 (Process flagSpam)
	 * COVERED INSTRUCTIONS IN THIS TEST: 97.7%
	 * COVERED INSTRUCTIONS IN AdministratorService: 23.2%
	 * COVERED DATA IN THIS TEST: 12%
	 * */

	@Test
	public void testFlagSpam() {

		final Object testingData[][] = {

			/**
			 * TESTING REQUIREMENT #24.2
			 * POSITIVE TEST
			 * COVERED INSTRUCTIONS: 97.7%
			 * COVERED DATA: 10%
			 * */
			{
				"admin", null
			},

			/**
			 * // * TESTING REQUIREMENT #24.2
			 * // * NEGATIVE TEST:You can not use the process being a company
			 * (Expected IllegalArgumentException)
			 * // * COVERED INSTRUCTIONS: 97.7%
			 * // * COVERED DATA: 10%
			 * // *
			 */

			{
				"company1", IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateFlagSpam((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}
	protected void templateFlagSpam(final String username, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);
			this.administratorService.flagSpamProccess();
			final int hackerId = this.getEntityId("hacker1");
			Assert.isTrue(!this.hackerService.findOne(hackerId).getFlagSpam() == false);
			this.administratorService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}
}
