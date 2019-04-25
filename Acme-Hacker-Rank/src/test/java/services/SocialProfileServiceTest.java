package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import repositories.SocialProfileRepository;

import domain.SocialProfile;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SocialProfileServiceTest extends AbstractTest{
	
	@Autowired
	SocialProfileRepository socialProfileRepository;
	
	@Autowired
	SocialProfileService socialProfileService;
	
	/**
	 * // * TESTING REQUIREMENT #23.1 (Manage Social Profiles : create)
	 * // * POSITIVETEST
	 * // * COVERED INSTRUCTIONS: 100%
	 * // * COVERED DATA: 100%
	 * // *
	 */
	@Test
	public void createSocialProfileGood() {
		authenticate("hacker1");
		SocialProfile s = socialProfileService.create();
		s.setNick("sp1");
		s.setLink("http://sp1.com");
		s.setSocialNetwork("sn1");
		
		socialProfileService.save(s);
		super.unauthenticate();
	}
	
	/**
	 * // * TESTING REQUIREMENT #23.1
	 * // * NEGATIVE TEST:YOU CANNOT CREATE A SOCIAL PROFILE WITHOUT A NICK
	 * (Expected ConstraintViolationException)
	 * // * COVERED INSTRUCTIONS: 100%
	 * // * COVERED DATA: 100%
	 * // *
	 */
	@Test(expected = ConstraintViolationException.class)
	public void createSocialProfileBad() {
		authenticate("hacker1");
		SocialProfile s = socialProfileService.create();
		s.setNick("");
		s.setLink("http://sp1.com");
		s.setSocialNetwork("sn1");
		
		socialProfileService.save(s);
		socialProfileRepository.flush();
		super.unauthenticate();
	}

}
