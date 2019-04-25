
package services;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.MiscellaneousData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MiscellaneousDataServiceTest extends AbstractTest {

	//SUT
	@Autowired
	HackerService				hackerService;

	@Autowired
	CurriculaService			curriculaService;

	@Autowired
	MiscellaneousDataService	miscellaneousDataService;


	/**
	 * TESTING REQUIREMENT #15 (Manage miscellaneous data in a curricula:CREATE)
	 * COVERED INSTRUCTIONS IN THIS TEST: 100%
	 * COVERED INSTRUCTIONS IN MISCELLANEOUSDATASERVICE: 69.4%
	 * COVERED DATA IN THIS TEST: 12%
	 * */

	@Test
	public void createMiscellaneousData() {
		this.authenticate("hacker1");
		final MiscellaneousData pd = this.miscellaneousDataService.create(this.getEntityId("curricula1"));
		pd.setFreeText("freeText");
		pd.setAttachments("Attachement1");

		final MiscellaneousData pd2 = this.miscellaneousDataService.create(this.getEntityId("curricula2"));
		pd2.setFreeText("");
		pd2.setAttachments("Attachement1");

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
			 * // * NEGATIVE TEST:YOU CANNOT CREATE A miscellaneousData WITHOUT FREETEXT
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
			this.template2((String) testingData[i][0], (MiscellaneousData) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	/**
	 * TESTING REQUIREMENT #15 (Manage miscellaneous data in a curricula:EDIT)
	 * COVERED INSTRUCTIONS IN THIS TEST: 100%
	 * COVERED INSTRUCTIONS IN MISCELLANEOUSDATASERVICE: 69.4%
	 * COVERED DATA IN THIS TEST: 12%
	 * */
	@Test
	public void editMiscellaneousData() {
		this.authenticate("hacker1");

		final List<MiscellaneousData> edL = this.miscellaneousDataService.findByCurriculaId(this.getEntityId("curricula1"));
		final MiscellaneousData md = edL.get(0);
		md.setFreeText("Cambio");

		this.unauthenticate();

		final Object testingData[][] = {

			/**
			 * TESTING REQUIREMENT #15
			 * POSITIVE TEST
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */
			{
				"hacker1", md, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template2((String) testingData[i][0], (MiscellaneousData) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * TESTING REQUIREMENT #15 (Manage miscellaneous data in a curricula:edit)
	 * COVERED INSTRUCTIONS IN THIS TEST: 100%
	 * COVERED INSTRUCTIONS IN MISCELLANEOUSDATASERVICE: 69.4%
	 * COVERED DATA IN THIS TEST: 12%
	 * */
	@Test
	public void editMiscellaneousData2() {
		this.authenticate("hacker1");
		final List<MiscellaneousData> edL2 = this.miscellaneousDataService.findByCurriculaId(this.getEntityId("curricula2"));
		final MiscellaneousData md2 = edL2.get(0);
		md2.setFreeText("");

		this.unauthenticate();

		final Object testingData[][] = {

			/**
			 * TESTING REQUIREMENT #15
			 * NEGATIVE TEST: YOU CANNOT EDIT AN miscellaneousData WITHOUT FREETEXT
			 * (Expected ConstraintViolationException)
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */

			{
				"hacker1", md2, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template2((String) testingData[i][0], (MiscellaneousData) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * TESTING REQUIREMENT #15 (Manage miscellaneous data in a curricula:DELETE)
	 * COVERED INSTRUCTIONS IN THIS TEST: 100%
	 * COVERED INSTRUCTIONS IN MISCELLANEOUSDATASERVICE: 69.4%
	 * COVERED DATA IN THIS TEST: 12%
	 * */
	@Test
	public void deleteMiscellaneousData() {
		this.authenticate("hacker1");
		final List<MiscellaneousData> edL = this.miscellaneousDataService.findByCurriculaId(this.getEntityId("curricula1"));
		final MiscellaneousData md = edL.get(0);
		md.getCurricula().setIsCopy(false);

		final List<MiscellaneousData> edL2 = this.miscellaneousDataService.findByCurriculaId(this.getEntityId("curricula2"));
		final MiscellaneousData md2 = edL2.get(0);
		md2.getCurricula().setIsCopy(true);

		this.unauthenticate();

		final Object testingData[][] = {

			/**
			 * TESTING REQUIREMENT #15
			 * POSITIVE TEST
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */
			{
				"hacker1", md, null
			},

			/**
			 * TESTING REQUIREMENT #15
			 * NEGATIVE TEST: YOU CANNOT DELETE MISCELLANEOUSDATA FROM A CURRICULA WICH IS A COPY
			 * (Expected IllegalArgumentException)
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */
			{
				"hacker1", md2, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateD((String) testingData[i][0], (MiscellaneousData) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void template2(final String username, final MiscellaneousData ed, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);
			this.miscellaneousDataService.save(ed);
			this.miscellaneousDataService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}
	protected void templateD(final String username, final MiscellaneousData ed, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);
			this.miscellaneousDataService.delete(ed);
			this.miscellaneousDataService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}
}
