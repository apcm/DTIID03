
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FinderRepository;
import repositories.HackerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.TickerGenerator;
import domain.Actor;
import domain.Box;
import domain.Customisation;
import domain.Finder;
import domain.Hacker;
import domain.SocialProfile;
import forms.HackerForm;

@Service
@Transactional
public class HackerService {

	@Autowired
	public HackerRepository		hackerRepository;

	@Autowired
	public ActorService			actorService;

	@Autowired
	public CustomisationService	customisationService;

	@Autowired
	public FinderService		finderService;

	@Autowired
	public FinderRepository		finderRepository;

	@Autowired
	public SocialProfileService	socialprofileService;


	//Constructor
	public HackerService() {
		super();
	}

	public Hacker create() {

		Hacker result;
		result = new Hacker();
		result.setComputed(false);

		final Collection<Box> predefined = new ArrayList<Box>();

		final UserAccount newUser = new UserAccount();
		final Authority f = new Authority();
		f.setAuthority(Authority.COMPANY);
		newUser.addAuthority(f);
		result.setUserAccount(newUser);
		result.getUserAccount().setStatus(true);

		result.setSocialProfiles(new ArrayList<SocialProfile>());
		result.setName("");
		result.setEmail("");
		result.setAddress("");
		result.setSurname("");
		result.setPhoneNumber("");
		result.setPhoto("");

		// Hacker
		final Finder fi = new Finder();
		result.setFinder(fi);

		return result;
	}

	public Hacker findByPrincipal() {
		Hacker res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.findByUserAccount(userAccount);
		Assert.notNull(res);

		return res;
	}

	public Hacker findOnePrincipal() {
		final Actor a = this.actorService.findByPrincipal();
		return this.hackerRepository.findOne(a.getId());
	}

	public Hacker findOne(final Hacker Hacker) {
		return this.hackerRepository.findOne(Hacker.getId());
	}

	public Collection<Hacker> findAll() {
		return this.hackerRepository.findAll();
	}

	public Hacker save(final Hacker hacker) {
		Assert.notNull(hacker);
		Assert.isTrue(!hacker.getIsBanned());

		final String pnumber = hacker.getPhoneNumber();
		final Customisation cus = ((List<Customisation>) this.customisationService.findAll()).get(0);
		final String cc = cus.getPhoneNumberCode();
		if (pnumber.matches("^[0-9]{4,}$"))
			hacker.setPhoneNumber(cc.concat(pnumber));

		if (hacker.getId() != 0) {
			Assert.isTrue(this.actorService.checkHacker());

			// Modified Hacker must be logged Hacker
			final Hacker logHacker;
			logHacker = this.findByPrincipal();
			Assert.notNull(logHacker);
			Assert.notNull(logHacker.getId());

		} else {

			final Collection<Box> boxes = this.actorService.createPredefinedBoxes();
			hacker.setBoxes(boxes);
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String oldpass = hacker.getUserAccount().getPassword();
			final String hash = encoder.encodePassword(oldpass, null);

			final UserAccount cuenta = hacker.getUserAccount();
			cuenta.setPassword(hash);
			hacker.setUserAccount(cuenta);

			final Finder find = new Finder();

			find.setMoment(new Date());
			final Finder find2 = this.finderRepository.save(find);

			hacker.setFinder(find2);
		}
		// Restrictions
		Hacker res;

		res = this.hackerRepository.save(hacker);
		return res;
	}

	public Hacker findByUserAccount(final UserAccount userAccount) {
		Hacker res;
		Assert.notNull(userAccount);

		res = this.hackerRepository.findByUserAccountId(userAccount.getId());

		return res;
	}

	public Hacker saveForTest(final Hacker bro) {

		// Restrictions
		Assert.isTrue(bro.getIsBanned() != true);

		if (bro.getId() == 0) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String oldpass = bro.getUserAccount().getPassword();
			final String hash = encoder.encodePassword(oldpass, null);

			final UserAccount cuenta = bro.getUserAccount();
			cuenta.setPassword(hash);
			bro.setUserAccount(cuenta);

		}
		if (bro.getId() == 0) {

		}

		return this.hackerRepository.save(bro);
	}
	public Hacker findOne(final int HackerId) {
		Hacker c;

		Assert.notNull(HackerId);
		Assert.isTrue(HackerId != 0);
		c = this.hackerRepository.findOne(HackerId);

		Assert.notNull(c);
		return c;
	}

	public Hacker reconstruct(final HackerForm hackerForm, final BindingResult binding) {
		final Hacker hacker = this.create();

		//Assert.isTrue(HackerForm.getUserAccount().getAuthorities() == colMem);
		//Damos valores a los atributos de la hermandad con los datos que nos llegan
		final Authority com = new Authority();
		com.setAuthority(Authority.HACKER);
		final List<Authority> aus = new ArrayList<>();
		aus.add(com);
		final UserAccount ua = hackerForm.getUserAccount();
		ua.setAuthorities(aus);

		hacker.setAddress(hackerForm.getAddress());
		hacker.setEmail(hackerForm.getEmail());
		hacker.setName(hackerForm.getName());
		hacker.setPhoneNumber(hackerForm.getPhoneNumber());
		hacker.setPhoto(hackerForm.getPhoto());
		hacker.setSurname(hackerForm.getSurname());
		hacker.setUserAccount(ua);
		hacker.setVat(hackerForm.getVat());

		hacker.setHolderName(hackerForm.getHolderName());
		hacker.setMakeName(hackerForm.getMakeName());
		hacker.setNumber(hackerForm.getNumber());
		hacker.setExpirationMonth(hackerForm.getExpirationMonth());
		hacker.setExpirationYear(hackerForm.getExpirationYear());
		hacker.setCvv(hackerForm.getCvv());

		final Finder find = new Finder();
		final Finder find2 = new Finder();
		find.setMoment(new Date());
		find2.setMoment(new Date());

		final Finder find3 = this.finderService.save(find);
		final Finder find4 = this.finderService.save(find2);

		hacker.setFinder(find3);
		hackerForm.setFinder(find4);

		hacker.setIsBanned(false);

		this.validator.validate(hacker, binding);
		if (binding.hasErrors())
			throw new ValidationException();

		return hacker;
	}


	@Autowired
	private Validator	validator;


	public Hacker reconstruct(final Hacker hacker, final BindingResult binding) {
		Hacker res;

		//Check authority
		final Authority a = new Authority();
		final Actor act = this.actorService.findByPrincipal();
		final UserAccount user = act.getUserAccount();
		a.setAuthority(Authority.HACKER);
		Assert.isTrue(user.getAuthorities().contains(a) && user.getAuthorities().size() == 1);

		if (hacker.getId() == 0)
			res = hacker;
		else
			res = this.hackerRepository.findOne(hacker.getId());
		res.setName(hacker.getName());
		res.setEmail(hacker.getEmail());
		res.setSurname(hacker.getSurname());
		res.setAddress(hacker.getAddress());
		res.setPhoneNumber(hacker.getPhoneNumber());
		res.setPhoto(hacker.getPhoto());
		res.setVat(hacker.getVat());

		res.setHolderName(hacker.getHolderName());
		res.setMakeName(hacker.getMakeName());
		res.setNumber(hacker.getNumber());
		res.setExpirationMonth(hacker.getExpirationMonth());
		res.setExpirationYear(hacker.getExpirationYear());
		res.setCvv(hacker.getCvv());

		this.validator.validate(res, binding);
		if (binding.hasErrors())
			throw new ValidationException();

		return res;
	}

	public Hacker getHackerByUserAccount(final int id) {
		return this.hackerRepository.findByUserAccountId(id);
	}

	public void leave() {
		final Hacker logHacker = this.findByPrincipal();

		logHacker.setAddress("Unknown");
		logHacker.setIsBanned(true);
		logHacker.setEmail("unknown@unknown.com");
		logHacker.setName("Unknown");
		logHacker.setPhoneNumber("Unknown");
		logHacker.setPhoto("http://www.unknown.com");

		logHacker.setSocialProfiles(null);
		logHacker.setSurname("Unknown");

		//		final Finder f = new Finder();
		//		final Finder find = this.finderService.save(f);
		//		logHacker.setFinder(find);

		logHacker.setHolderName("Unknown");
		logHacker.setMakeName("Unknown");
		logHacker.setCvv(123);
		logHacker.setExpirationMonth(1);
		logHacker.setExpirationYear(9999);
		logHacker.setNumber("4532134223318979");

		this.hackerRepository.flush();

		final UserAccount ua = logHacker.getUserAccount();
		final String tick1 = TickerGenerator.tickerLeave();
		if (logHacker.getSocialProfiles() != null)
			for (final SocialProfile sp : logHacker.getSocialProfiles())
				this.socialprofileService.deleteLeave(sp);

		ua.setUsername("Unknown" + tick1);
		final String pass1 = TickerGenerator.generateTicker();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String pass2 = encoder.encodePassword(pass1, null);
		ua.setPassword(pass2);
		logHacker.setUserAccount(ua);
	}

	public void flush() {
		this.hackerRepository.flush();
	}

}
