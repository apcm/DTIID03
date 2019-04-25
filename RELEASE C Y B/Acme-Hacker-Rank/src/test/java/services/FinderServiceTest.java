
package services;

import java.util.Calendar;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.Validator;

import repositories.FinderRepository;
import utilities.AbstractTest;
import domain.Finder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class FinderServiceTest extends AbstractTest {

	@Autowired
	FinderService		finderService;

	@Autowired
	FinderRepository	repository;
	@Autowired
	Validator			validator;


	@Test
	public void testFinder() {
		super.authenticate("hacker1");
		final int finderId = super.getEntityId("finder1");
		final Finder f = this.finderService.findOne(finderId);
		f.setKeyword("cod");
		f.setDeadline(Calendar.getInstance().getTime());
		f.setMaximumDeadline(Calendar.getInstance().getTime());
		f.setMinimumSalary(2000);
		this.finderService.save(f);

		super.unauthenticate();
	}

	@Test(expected = ConstraintViolationException.class)
	public void testFinderBadSalary() {
		super.authenticate("hacker1");
		final int finderId = super.getEntityId("finder1");
		final Finder f = this.finderService.findOne(finderId);
		f.setKeyword("cod");
		f.setDeadline(Calendar.getInstance().getTime());
		f.setMaximumDeadline(Calendar.getInstance().getTime());
		f.setMinimumSalary(-500);
		this.finderService.save(f);
		this.repository.flush();
		super.unauthenticate();
	}
}
