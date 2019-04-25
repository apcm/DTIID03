
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
import domain.PersonalData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PersonalDataServiceTest extends AbstractTest {

	//SUT
	@Autowired
	HackerService		hackerService;

	@Autowired
	CurriculaService	curriculaService;

	@Autowired
	PersonalDataService	personalDataService;


	/**
	 * TESTING REQUIREMENT #15 (Manage personal data in a curricula:CREATE)
	 * POSITIVE TEST
	 * COVERED INSTRUCTIONS IN THIS TEST: 100%
	 * COVERED INSTRUCTIONS IN PERSONALDATASERVICE: 65.6%
	 * COVERED DATA IN THIS TEST: 12%
	 * */

	@Test
	public void createPersonalData() {
		this.authenticate("hacker1");
		final PersonalData pd = this.personalDataService.create(this.getEntityId("curricula1"));
		pd.setFullName("Pepe Gonzalez");
		pd.setGitProfile("http://adsasf.com");
		pd.setLinkedInProfile("http://adsasf.com");
		pd.setPhoneNumber("676893423");
		pd.setStatement("Statement");

		final PersonalData pd2 = this.personalDataService.create(this.getEntityId("curricula1"));
		pd2.setFullName("Pepe Gonzalez");
		pd2.setGitProfile("http://adsasf.com");
		pd2.setLinkedInProfile("http://adsasf.com");
		pd2.setPhoneNumber("676893423");
		pd2.setStatement("");

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
			 * // * NEGATIVE TEST:YOU CANNOT CREATE A personalDATE WITHOUT DEGREE
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
			this.template2((String) testingData[i][0], (PersonalData) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	/**
	 * TESTING REQUIREMENT #15 (Manage personal data in a curricula:EDIT)
	 * POSITIVE TEST
	 * COVERED INSTRUCTIONS IN THIS TEST: 100%
	 * COVERED INSTRUCTIONS IN PERSONALDATASERVICE: 65.6%
	 * COVERED DATA IN THIS TEST: 12%
	 * */
	@Test
	public void editpersonalData() {
		this.authenticate("hacker1");

		final List<PersonalData> edL = this.personalDataService.findByCurriculaId(this.getEntityId("curricula1"));
		final PersonalData pd = edL.get(0);
		pd.setFullName("TOMÁS CABELLO LÓPEZ");

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
			this.template2((String) testingData[i][0], (PersonalData) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * TESTING REQUIREMENT #15 (Manage personal data in a curricula:EDIT)
	 * POSITIVE TEST
	 * COVERED INSTRUCTIONS IN THIS TEST: 100%
	 * COVERED INSTRUCTIONS IN PERSONALDATASERVICE: 65.6%
	 * COVERED DATA IN THIS TEST: 12%
	 * */
	@Test
	public void editpersonalData2() {
		this.authenticate("hacker1");
		final List<PersonalData> edL2 = this.personalDataService.findByCurriculaId(this.getEntityId("curricula2"));
		final PersonalData pd2 = edL2.get(0);
		pd2.setLinkedInProfile("");

		this.unauthenticate();

		final Object testingData[][] = {

			/**
			 * TESTING REQUIREMENT #15
			 * NEGATIVE TEST: YOU CANNOT EDIT A PERSONAL DATA WITHOUT LinkedInProfile
			 * (Expected ConstraintViolationException)
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */

			{
				"hacker1", pd2, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template2((String) testingData[i][0], (PersonalData) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * TESTING REQUIREMENT #15 (Manage personal data in a curricula:DELETE)
	 * POSITIVE TEST
	 * COVERED INSTRUCTIONS IN THIS TEST: 100%
	 * COVERED INSTRUCTIONS IN PERSONALDATASERVICE: 65.6%
	 * COVERED DATA IN THIS TEST: 12%
	 * */
	@Test
	public void deletePersonalData() {
		this.authenticate("hacker1");
		final List<PersonalData> edL = this.personalDataService.findByCurriculaId(this.getEntityId("curricula1"));
		final PersonalData ed = edL.get(0);
		ed.getCurricula().setIsCopy(false);

		final List<PersonalData> edL2 = this.personalDataService.findByCurriculaId(this.getEntityId("curricula2"));
		final PersonalData ed2 = edL2.get(0);
		ed2.getCurricula().setIsCopy(true);

		this.unauthenticate();

		final Object testingData[][] = {

			/**
			 * TESTING REQUIREMENT #15
			 * POSITIVE TEST
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */
			{
				"hacker1", ed, null
			},

			/**
			 * TESTING REQUIREMENT #15
			 * NEGATIVE TEST: YOU CANNOT DELETE A CURRICULA WICH IS A COPY
			 * (Expected IllegalArgumentException)
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */
			{
				"hacker1", ed2, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateD((String) testingData[i][0], (PersonalData) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void template2(final String username, final PersonalData ed, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);
			this.personalDataService.save(ed);
			this.personalDataService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}
	protected void templateD(final String username, final PersonalData ed, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);
			this.personalDataService.delete(ed);
			this.personalDataService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

}
