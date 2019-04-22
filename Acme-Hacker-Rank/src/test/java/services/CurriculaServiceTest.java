
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Curricula;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CurriculaServiceTest extends AbstractTest {

	//SUT
	@Autowired
	HackerService				hackerService;

	@Autowired
	CurriculaService			curriculaService;

	@Autowired
	PersonalDataService			personalDataService;

	@Autowired
	PositionDataService			positionDataService;

	@Autowired
	MiscellaneousDataService	miscellaneousDataService;


	/**
	 * TESTING REQUIREMENT #15 (Manage curricula.)
	 * POSITIVE TEST
	 * COVERED INSTRUCTIONS IN THIS TEST: 100%
	 * COVERED INSTRUCTIONS IN LinkRecordService: 22.9%
	 * COVERED DATA IN THIS TEST: 12%
	 * */

	@Test
	public void createCurricula() {
		this.authenticate("hacker1");
		final Curricula c = this.curriculaService.create();
		c.setName("Curricula1");

		final Curricula c2 = this.curriculaService.create();
		c.setName("");

		this.unauthenticate();

		this.authenticate("company1");
		final Curricula c3 = this.curriculaService.create();
		c.setName("Curricula3");
		this.unauthenticate();

		final Object testingData[][] = {

			/**
			 * TESTING REQUIREMENT #9.2
			 * POSITIVE TEST
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */
			{
				"hacker1", c, null
			},

			/**
			 * // * TESTING REQUIREMENT #9.2
			 * // * NEGATIVE TEST:YOU CANNOT CREATE A CURRICULA WITHOUT NAME
			 * (Expected IllegalArgumentException)
			 * // * COVERED INSTRUCTIONS: 100%
			 * // * COVERED DATA: 10%
			 * // *
			 */

			{
				"hacker2", c2, IllegalArgumentException.class
			},

			/**
			 * TESTING REQUIREMENT #9.2
			 * NEGATIVE TEST: YOU CANNOT CREATE A CURRICULA BEING A COMPANY
			 * (Expected NullPointerException)
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */
			{
				"hacker1", c3, NullPointerException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.template2((String) testingData[i][0], (Curricula) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void editCurricula() {
		this.authenticate("hacker1");
		final Curricula c = this.curriculaService.findOne(this.getEntityId("curricula1"));
		c.setName("C1");
		c.setIsCopy(false);
		c.setApplication(null);

		//p3->finalMode=false
		final Curricula c2 = this.curriculaService.findOne(this.getEntityId("curricula2"));
		c2.setIsCopy(true);

		this.unauthenticate();

		final Object testingData[][] = {

			/**
			 * TESTING REQUIREMENT #9.2
			 * POSITIVE TEST
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */
			{
				"hacker1", c, null
			},

			/**
			 * TESTING REQUIREMENT #9.2
			 * NEGATIVE TEST: YOU CANNOT EDIT A COPY OF A CURRICULA
			 * (Expected NullPointerException)
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */
			{
				"hacker1", c2, NullPointerException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.template2((String) testingData[i][0], (Curricula) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void deleteCurricula() {
		this.authenticate("hacker1");
		final Curricula c = this.curriculaService.findOne(this.getEntityId("curricula1"));
		c.setIsCopy(true);

		final Curricula c2 = this.curriculaService.findOne(this.getEntityId("curricula2"));

		this.unauthenticate();

		final Object testingData[][] = {

			/**
			 * TESTING REQUIREMENT #9.2
			 * POSITIVE TEST
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */
			{
				"hacker1", c2, null
			},

			/**
			 * TESTING REQUIREMENT #9.2
			 * NEGATIVE TEST: YOU CANNOT DELETE A CURRICULA WICH IS A COPY
			 * (Expected IllegalArgumentException)
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */
			{
				"hacker1", c, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateD((String) testingData[i][0], (Curricula) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void template2(final String username, final Curricula c, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);
			this.curriculaService.save(c);
			this.curriculaService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	protected void templateD(final String username, final Curricula c, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);
			this.curriculaService.delete(c);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}
}
