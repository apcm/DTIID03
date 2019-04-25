
package services;

import javax.transaction.Transactional;

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
	 * TESTING REQUIREMENT #15 (Manage personal data in a curricula)
	 * POSITIVE TEST
	 * COVERED INSTRUCTIONS IN THIS TEST: 100%
	 * COVERED INSTRUCTIONS IN LinkRecordService: 22.9%
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

		//		final PersonalData pd2 = this.personalDataService.create(this.getEntityId("curricula1"));
		//		pd.setFullName("Pepe Gonzalez");
		//		pd.setGitProfile("http://adsasf.com");
		//		pd.setLinkedInProfile("http://adsasf.com");
		//		pd.setPhoneNumber("676893423");
		//		pd.setStatement("");

		this.unauthenticate();

		final Object testingData[][] = {

			/**
			 * TESTING REQUIREMENT #9.2
			 * POSITIVE TEST
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */
			{
				"hacker1", pd, null
			}

		/**
		 * // * TESTING REQUIREMENT #9.2
		 * // * NEGATIVE TEST:YOU CANNOT CREATE A personalDATE WITHOUT DEGREE
		 * (Expected ConstraintViolationException)
		 * // * COVERED INSTRUCTIONS: 100%
		 * // * COVERED DATA: 10%
		 * // *
		 */

		//			{
		//				"hacker1", pd2, ConstraintViolationException.class
		//			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.template2((String) testingData[i][0], (PersonalData) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	//	@Test
	//	public void editpersonalData() {
	//		this.authenticate("hacker1");
	//		final List<personalData> edL = this.personalDataService.findByCurriculaId(this.getEntityId("curricula1"));
	//		final personalData ed20 = edL.get(0);
	//		ed20.setInstitution("ETSII2");
	//		ed20.setInstitution("Institution1");
	//		ed20.setMark(8.0);
	//		Date d1 = new Date();
	//		d1 = DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime("2005-06-12").toDate();
	//		Date d2 = new Date();
	//		d2 = DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime("2005-07-12").toDate();
	//		ed20.setStartMoment(d1);
	//		ed20.setEndMoment(d2);
	//
	//		this.unauthenticate();
	//
	//		final Object testingData[][] = {
	//
	//			/**
	//			 * TESTING REQUIREMENT #9.2
	//			 * POSITIVE TEST
	//			 * COVERED INSTRUCTIONS: 100%
	//			 * COVERED DATA: 10%
	//			 * */
	//			{
	//				"hacker1", ed20, null
	//			}
	//		};
	//
	//		for (int i = 0; i < testingData.length; i++)
	//			this.template2((String) testingData[i][0], (personalData) testingData[i][1], (Class<?>) testingData[i][2]);
	//	}
	//
	//	public void editpersonalData2() {
	//		this.authenticate("hacker1");
	//		final List<personalData> edL = this.personalDataService.findByCurriculaId(this.getEntityId("curricula1"));
	//		Date d1 = new Date();
	//		d1 = DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime("2005-06-12").toDate();
	//		Date d2 = new Date();
	//		d2 = DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime("2005-07-12").toDate();
	//
	//		final personalData ed2 = edL.get(1);
	//		ed2.setEndMoment(d2);
	//		ed2.setStartMoment(d1);
	//		ed2.setInstitution("");
	//
	//		this.unauthenticate();
	//
	//		final Object testingData[][] = {
	//
	//			/**
	//			 * TESTING REQUIREMENT #9.2
	//			 * NEGATIVE TEST: YOU CANNOT EDIT A ED WITHOUT DEGREE
	//			 * (Expected ConstraintViolationException)
	//			 * COVERED INSTRUCTIONS: 100%
	//			 * COVERED DATA: 10%
	//			 * */
	//			{
	//				"hacker1", ed2, ConstraintViolationException.class
	//			}
	//
	//		};
	//
	//		for (int i = 0; i < testingData.length; i++)
	//			this.template2((String) testingData[i][0], (personalData) testingData[i][1], (Class<?>) testingData[i][2]);
	//	}
	//	@Test
	//	public void deletepersonalData() {
	//		this.authenticate("hacker1");
	//		final List<personalData> edL = this.personalDataService.findByCurriculaId(this.getEntityId("curricula1"));
	//		final personalData ed = edL.get(0);
	//
	//		this.unauthenticate();
	//
	//		final Object testingData[][] = {
	//
	//			/**
	//			 * TESTING REQUIREMENT #9.2
	//			 * POSITIVE TEST
	//			 * COVERED INSTRUCTIONS: 100%
	//			 * COVERED DATA: 10%
	//			 * */
	//			{
	//				"hacker1", ed, null
	//			}
	//
	//		};
	//
	//		for (int i = 0; i < testingData.length; i++)
	//			this.templateD((String) testingData[i][0], (personalData) testingData[i][1], (Class<?>) testingData[i][2]);
	//	}
	//
	//	public void deletepersonalData2() {
	//		this.authenticate("hacker1");
	//		final List<personalData> edL = this.personalDataService.findByCurriculaId(this.getEntityId("curricula1"));
	//		final personalData ed2 = edL.get(1);
	//		ed2.getCurricula().setIsCopy(true);
	//
	//		this.unauthenticate();
	//
	//		final Object testingData[][] = {
	//
	//			/**
	//			 * TESTING REQUIREMENT #9.2
	//			 * NEGATIVE TEST: YOU CANNOT DELETE A CURRICULA WICH IS A COPY
	//			 * (Expected IllegalArgumentException)
	//			 * COVERED INSTRUCTIONS: 100%
	//			 * COVERED DATA: 10%
	//			 * */
	//			{
	//				"hacker1", ed2, IllegalArgumentException.class
	//			}
	//
	//		};
	//
	//		for (int i = 0; i < testingData.length; i++)
	//			this.templateD((String) testingData[i][0], (personalData) testingData[i][1], (Class<?>) testingData[i][2]);
	//	}

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
