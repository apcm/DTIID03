
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
import domain.EducationData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class EducationDataServiceTest extends AbstractTest {

	//SUT
	@Autowired
	HackerService			hackerService;

	@Autowired
	CurriculaService		curriculaService;

	@Autowired
	EducationDataService	educationDataService;


	/**
	 * TESTING REQUIREMENT #15 (Manage education data in a curricula:create)
	 * COVERED INSTRUCTIONS IN THIS TEST: 100%
	 * COVERED INSTRUCTIONS IN EducationService: 64.7%
	 * COVERED DATA IN THIS TEST: 12%
	 * */

	@Test
	public void createEducationData() {
		this.authenticate("hacker1");
		final EducationData ed = this.educationDataService.create(this.getEntityId("curricula1"));
		ed.setDegree("Degree1");
		Date d1 = new Date();
		d1 = DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime("2005-06-12").toDate();
		Date d2 = new Date();
		d2 = DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime("2005-07-12").toDate();
		ed.setStartMoment(d1);
		ed.setEndMoment(d2);
		ed.setInstitution("ETSII");
		ed.setMark(8.0);

		final EducationData ed2 = this.educationDataService.create(this.getEntityId("curricula1"));
		ed2.setDegree("");

		ed2.setStartMoment(d1);
		ed2.setEndMoment(d2);
		ed2.setInstitution("ETSII");
		ed2.setMark(8.0);

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
			 * // * TESTING REQUIREMENT #15
			 * // * NEGATIVE TEST:YOU CANNOT CREATE A EDUCATIONDATE WITHOUT DEGREE
			 * (Expected ConstraintViolationException)
			 * // * COVERED INSTRUCTIONS: 100%
			 * // * COVERED DATA: 10%
			 * // *
			 */

			{
				"hacker1", ed2, ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.template2((String) testingData[i][0], (EducationData) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	/**
	 * TESTING REQUIREMENT #15 (Manage education data in a curricula:EDIT)
	 * COVERED INSTRUCTIONS IN THIS TEST: 100%
	 * COVERED INSTRUCTIONS IN EducationService: 64.7%
	 * COVERED DATA IN THIS TEST: 12%
	 * */
	@Test
	public void editEducationData() {
		this.authenticate("hacker1");
		final List<EducationData> edL = this.educationDataService.findByCurriculaId(this.getEntityId("curricula1"));
		final EducationData ed20 = edL.get(0);
		ed20.setInstitution("ETSII2");
		ed20.setInstitution("Institution1");
		ed20.setMark(8.0);
		Date d1 = new Date();
		d1 = DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime("2005-06-12").toDate();
		Date d2 = new Date();
		d2 = DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime("2005-07-12").toDate();
		ed20.setStartMoment(d1);
		ed20.setEndMoment(d2);

		this.unauthenticate();

		final Object testingData[][] = {

			/**
			 * TESTING REQUIREMENT #15
			 * POSITIVE TEST
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */
			{
				"hacker1", ed20, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template2((String) testingData[i][0], (EducationData) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	/**
	 * TESTING REQUIREMENT #15 (Manage education data in a curricula:EDIT)
	 * COVERED INSTRUCTIONS IN THIS TEST: 100%
	 * COVERED INSTRUCTIONS IN EducationService: 64.7%
	 * COVERED DATA IN THIS TEST: 12%
	 * */
	@Test
	public void editEducationData2() {
		this.authenticate("hacker1");
		final List<EducationData> edL = this.educationDataService.findByCurriculaId(this.getEntityId("curricula1"));
		Date d1 = new Date();
		d1 = DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime("2005-06-12").toDate();
		Date d2 = new Date();
		d2 = DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime("2005-07-12").toDate();

		final EducationData ed2 = edL.get(1);
		ed2.setEndMoment(d2);
		ed2.setStartMoment(d1);
		ed2.setInstitution("");

		this.unauthenticate();

		final Object testingData[][] = {

			/**
			 * TESTING REQUIREMENT #15
			 * NEGATIVE TEST: YOU CANNOT EDIT AN EDUCATION DATA WITHOUT DEGREE
			 * (Expected ConstraintViolationException)
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */
			{
				"hacker1", ed2, ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.template2((String) testingData[i][0], (EducationData) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * TESTING REQUIREMENT #15 (Manage education data in a curricula:DELETE)
	 * COVERED INSTRUCTIONS IN THIS TEST: 100%
	 * COVERED INSTRUCTIONS IN EducationService: 64.7%
	 * COVERED DATA IN THIS TEST: 12%
	 * */
	@Test
	public void deleteEducationData() {
		this.authenticate("hacker1");
		final List<EducationData> edL = this.educationDataService.findByCurriculaId(this.getEntityId("curricula1"));
		final EducationData ed = edL.get(0);

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
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateD((String) testingData[i][0], (EducationData) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * TESTING REQUIREMENT #15 (Manage education data in a curricula:DELETE)
	 * COVERED INSTRUCTIONS IN THIS TEST: 100%
	 * COVERED INSTRUCTIONS IN EducationService: 64.7%
	 * COVERED DATA IN THIS TEST: 12%
	 * */
	@Test
	public void deleteEducationData2() {
		this.authenticate("hacker1");
		final List<EducationData> edL = this.educationDataService.findByCurriculaId(this.getEntityId("curricula1"));
		final EducationData ed2 = edL.get(1);
		ed2.getCurricula().setIsCopy(true);

		this.unauthenticate();

		final Object testingData[][] = {

			/**
			 * TESTING REQUIREMENT #15
			 * NEGATIVE TEST: YOU CANNOT DELETE A EDUCATIONDATA FROM A CURRICULA WICH IS A COPY
			 * (Expected IllegalArgumentException)
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */
			{
				"hacker1", ed2, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateD((String) testingData[i][0], (EducationData) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void template2(final String username, final EducationData ed, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);
			this.educationDataService.save(ed);
			this.educationDataService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}
	protected void templateD(final String username, final EducationData ed, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);
			this.educationDataService.delete(ed);
			this.educationDataService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

}
