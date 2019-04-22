
package services;

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
public class DashboardServiceTest extends AbstractTest {

	@Autowired
	DashboardService	dashboardService;


	@Test
	/**
	 * TESTING REQUIREMENT #11.2
	 * POSITIVE TEST
	 * COVERED INSTRUCTIONS: 6.25%
	 * COVERED DATA: 100%
	 * */
	public void testAvgPositions() {
		super.authenticate("admin");
		final double avg = this.dashboardService.avgPositions();
		final double value = 7.0;
		Assert.isTrue(avg == value);
		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	/**
	 * TESTING REQUIREMENT #11.2
	 * NEGATIVE TEST
	 * COVERED INSTRUCTIONS: 100%
	 * COVERED DATA: 100%
	 * */
	public void testAvgPositionsNegative() {
		super.authenticate("admin");
		final double avg = this.dashboardService.avgPositions();
		final double value = 7.5;
		Assert.isTrue(avg == value);
		super.unauthenticate();
	}

	@Test
	/**
	 * TESTING REQUIREMENT #18
	 * NEGATIVE TEST
	 * COVERED INSTRUCTIONS: 100%
	 * COVERED DATA: 100%
	 * */
	public void testAvgCurriculum() {
		super.authenticate("admin");
		final double avg = this.dashboardService.avgCurricula();
		final double value = 2.0;
		Assert.isTrue(avg == value);
		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	/**
	 * TESTING REQUIREMENT #18
	 * NEGATIVE TEST
	 * COVERED INSTRUCTIONS: 100%
	 * COVERED DATA: 100%
	 * */
	public void testAvgCurriculaNegative() {
		super.authenticate("admin");
		final double avg = this.dashboardService.avgCurricula();
		final double value = 7.0;
		Assert.isTrue(avg == value);
		super.unauthenticate();
	}
}
