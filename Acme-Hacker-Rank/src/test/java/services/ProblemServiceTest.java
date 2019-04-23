
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Problem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ProblemServiceTest extends AbstractTest {

	//SUT
	@Autowired
	private ProblemService	problemService;


	/**
	 * TESTING REQUIREMENT #9.2 (Manage problems database when you are a company: create)
	 * POSITIVE TEST
	 * COVERED INSTRUCTIONS IN THIS TEST: 100%
	 * COVERED INSTRUCTIONS IN ProblemService: 71.1%
	 * COVERED DATA IN THIS TEST: 60%
	 * */

	@Test
	public void createProblem() {
		this.authenticate("company1");
		final Problem p = this.problemService.create();
		p.setStatement("Esto es un statement");
		p.setAttachments("Sample address");
		p.setFinalMode(false);
		p.setHint("Hint1");
		p.setTitle("title1");

		final Problem p2 = this.problemService.create();
		p2.setStatement("");
		p2.setAttachments("Sample address");
		p2.setFinalMode(false);
		p2.setHint("Hint1");
		p2.setTitle("title1");

		final Problem p3 = this.problemService.create();
		p3.setStatement("Esto es un statement");
		p3.setAttachments("Sample address");
		p3.setFinalMode(false);
		p3.setHint("Hint1");
		p3.setTitle("");

		final Problem p4 = this.problemService.create();
		p4.setStatement("Esto es un statement");
		p4.setAttachments("Sample address");
		p4.setFinalMode(false);
		p4.setHint("Hint1");
		p4.setTitle("title1");

		this.unauthenticate();

		this.authenticate("hacker1");
		final Problem p5 = this.problemService.create();
		p5.setStatement("Esto es un statement");
		p5.setAttachments("Sample address");
		p5.setFinalMode(false);
		p5.setHint("");
		p5.setTitle("title1");

		this.unauthenticate();

		final Object testingData[][] = {

			/**
			 * TESTING REQUIREMENT #9.2
			 * POSITIVE TEST
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */
			{
				"company1", p, null
			},

			/**
			 * // * TESTING REQUIREMENT #9.2
			 * // * POSITIVE TEST
			 * // * COVERED INSTRUCTIONS: 100%
			 * // * COVERED DATA: 10%
			 * // *
			 */

			{
				"company1", p4, null
			},

			/**
			 * TESTING REQUIREMENT #9.2
			 * NEGATIVE TEST: YOU CANNOT CREATE A PROBLEM WITH NO TITLE
			 * (Expected ConstraintViolationException)
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */
			{
				"company1", p3, ConstraintViolationException.class
			},

			/**
			 * TESTING REQUIREMENT #9.2
			 * NEGATIVE TEST: YOU CANNOT CREATE A PROBLEM WITH NO STATEMENT
			 * (Expected ConstraintViolationException)
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */
			{
				"company1", p2, ConstraintViolationException.class
			},

			/**
			 * TESTING REQUIREMENT #9.2
			 * NEGATIVE TEST: YOU CANNOT CREATE A PROBLEM BEING A HACKER
			 * (Expected ConstraintViolationException)
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */
			{
				"company1", p5, NullPointerException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.template2((String) testingData[i][0], (Problem) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * TESTING REQUIREMENT #9.2 (Manage problems database when you are a company: edit)
	 * POSITIVE TEST
	 * COVERED INSTRUCTIONS IN THIS TEST: 100%
	 * COVERED INSTRUCTIONS IN ProblemService: 71.1%
	 * COVERED DATA IN THIS TEST: 30%
	 * */

	@Test
	public void editProblem() {
		this.authenticate("company1");
		//p3->finalMode=false
		final Problem p3 = this.problemService.findOne(this.getEntityId("problem3"));

		p3.setStatement("Statement1");
		p3.setFinalMode(false);
		p3.setHint("nj");

		this.unauthenticate();

		final Object testingData[][] = {

			/**
			 * TESTING REQUIREMENT #9.2
			 * POSITIVE TEST
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */
			{
				"company1", p3, null
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.template2((String) testingData[i][0], (Problem) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * TESTING REQUIREMENT #9.2 (Manage problems database when you are a company: edit)
	 * POSITIVE TEST
	 * COVERED INSTRUCTIONS IN THIS TEST: 100%
	 * COVERED INSTRUCTIONS IN ProblemService: 71.1%
	 * COVERED DATA IN THIS TEST: 60%
	 * */
	@Test
	public void editProblem2() {
		this.authenticate("company1");
		final Problem p = this.problemService.findOne(this.getEntityId("problem1"));
		p.setStatement("Esto es un statement");
		p.setAttachments("Sample address");
		p.setFinalMode(false);
		p.setHint("Hint1");
		p.setTitle("");

		this.unauthenticate();

		final Object testingData[][] = {

			/**
			 * TESTING REQUIREMENT #9.2
			 * NEGATIVE TEST: YOU CANNOT EDIT A PROBLEM WITH NO TITLE
			 * (Expected ConstraintViolationException)
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */
			{
				"company1", p, ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.template2((String) testingData[i][0], (Problem) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	/**
	 * TESTING REQUIREMENT #9.2 (Manage problems database when you are a company: delete)
	 * POSITIVE TEST
	 * COVERED INSTRUCTIONS IN THIS TEST: 100%
	 * COVERED INSTRUCTIONS IN ProblemService: 71.1%
	 * COVERED DATA IN THIS TEST: 60%
	 * */
	@Test
	public void deleteProblem() {
		this.authenticate("company1");
		final Problem p = this.problemService.findOne(this.getEntityId("problem1"));
		p.setFinalMode(true);

		//p3->finalMode=false
		final Problem p3 = this.problemService.findOne(this.getEntityId("problem3"));

		this.unauthenticate();

		final Object testingData[][] = {

			/**
			 * TESTING REQUIREMENT #9.2
			 * POSITIVE TEST
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */
			{
				"company1", p3, null
			},

			/**
			 * TESTING REQUIREMENT #9.2
			 * NEGATIVE TEST: YOU CANNOT DELETE A PROBLEM WITH FINAL MODE = TRUE
			 * (Expected IllegalArgumentException)
			 * COVERED INSTRUCTIONS: 100%
			 * COVERED DATA: 10%
			 * */
			{
				"company1", p, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateD((String) testingData[i][0], (Problem) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void template2(final String username, final Problem p, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);
			this.problemService.save(p);
			this.problemService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	protected void templateD(final String username, final Problem p, final Class<?> expected) {

		Class<?> caught;

		caught = null;

		try {
			this.authenticate(username);
			this.problemService.deleteProblem(p);
			this.problemService.flush();

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}
}
