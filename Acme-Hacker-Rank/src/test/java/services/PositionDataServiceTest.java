
package services;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.joda.time.format.DateTimeFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.PositionData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PositionDataServiceTest extends AbstractTest {

	//SUT
	@Autowired
	HackerService		hackerService;

	@Autowired
	CurriculaService	curriculaService;

	@Autowired
	PositionDataService	positionDataService;


	/**
	 * TESTING REQUIREMENT #15 (Manage position data in a curricula:CREATE)
	 * POSITIVE TEST
	 * COVERED INSTRUCTIONS IN THIS TEST: 100%
	 * COVERED INSTRUCTIONS IN PositionDataService: 66.8%
	 * COVERED DATA IN THIS TEST: 12%
	 * */

	@Test
	public void createPositionData() {
		this.authenticate("hacker1");
		final PositionData pd = this.positionDataService.create(this.getEntityId("curricula1"));
		pd.setDescription("Descrption1");
		Date d1 = new Date();
		d1 = DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime("2005-06-12").toDate();
		pd.setStartMoment(d1);
		pd.setTitle("Title1");

		final PositionData pd2 = this.positionDataService.create(this.getEntityId("curricula2"));
		pd2.setDescription("Description1");
		pd2.setStartMoment(d1);
		pd2.setTitle("");

		this.unauthenticate();

		final Object testingData[][] = {

			/**
			 * TESTING REQUIREMENT #15
			 * POSITIVE TEST
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */
			{
				"hacker1", pd, null
			},

			/**
			 * // * TESTING REQUIREMENT #15
			 * // * NEGATIVE TEST:YOU CANNOT CREATE A positionData WITHOUT TITLE
			 * (Expected ConstraintViolationException)
			 * // * COVERED INSTRUCTIONS: 100%
			 * // * COVERED DATA: 10%
			 * // *
			 */

			{
				"hacker1", pd2, ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.template2((String) testingData[i][0], (PositionData) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	/**
	 * TESTING REQUIREMENT #15 (Manage position data in a curricula:EDIT)
	 * POSITIVE TEST
	 * COVERED INSTRUCTIONS IN THIS TEST: 100%
	 * COVERED INSTRUCTIONS IN PositionDataService: 66.8%
	 * COVERED DATA IN THIS TEST: 12%
	 * */

	@Test
	public void editpositionData() {
		this.authenticate("hacker1");

		final List<PositionData> edL = this.positionDataService.findByCurriculaId(this.getEntityId("curricula1"));
		final PositionData pd = edL.get(0);
		pd.setDescription("Cambio");

		this.unauthenticate();

		final Object testingData[][] = {

			/**
			 * TESTING REQUIREMENT #15
			 * POSITIVE TEST
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */
			{
				"hacker1", pd, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template2((String) testingData[i][0], (PositionData) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void editpositionData2() {
		this.authenticate("hacker1");
		final List<PositionData> edL2 = this.positionDataService.findByCurriculaId(this.getEntityId("curricula2"));
		final PositionData pd2 = edL2.get(0);
		pd2.setDescription("");

		this.unauthenticate();

		final Object testingData[][] = {

			/**
			 * TESTING REQUIREMENT #15
			 * NEGATIVE TEST: YOU CANNOT EDIT AN PositionData WITHOUT DESCRIPTION
			 * (Expected ConstraintViolationException)
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */

			{
				"hacker1", pd2, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template2((String) testingData[i][0], (PositionData) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * TESTING REQUIREMENT #15 (Manage position data in a curricula:DELETE)
	 * POSITIVE TEST
	 * COVERED INSTRUCTIONS IN THIS TEST: 100%
	 * COVERED INSTRUCTIONS IN PositionDataService: 66.8%
	 * COVERED DATA IN THIS TEST: 12%
	 * */

	@Test
	public void deletePositionData() {
		this.authenticate("hacker1");
		final List<PositionData> edL = this.positionDataService.findByCurriculaId(this.getEntityId("curricula1"));
		final PositionData pd = edL.get(0);
		pd.getCurricula().setIsCopy(false);

		final List<PositionData> edL2 = this.positionDataService.findByCurriculaId(this.getEntityId("curricula2"));
		final PositionData pd2 = edL2.get(0);
		pd2.getCurricula().setIsCopy(true);

		this.unauthenticate();

		final Object testingData[][] = {

			/**
			 * TESTING REQUIREMENT #15
			 * POSITIVE TEST
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */
			{
				"hacker1", pd, null
			},

			/**
			 * TESTING REQUIREMENT #15
			 * NEGATIVE TEST: YOU CANNOT DELETE A POSITION FROM A CURRICULA WICH IS A COPY
			 * (Expected IllegalArgumentException)
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */
			{
				"hacker1", pd2, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateD((String) testingData[i][0], (PositionData) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void template2(final String username, final PositionData ed, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);
			this.positionDataService.save(ed);
			this.positionDataService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}
	protected void templateD(final String username, final PositionData ed, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);
			this.positionDataService.delete(ed);
			this.positionDataService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}
}
