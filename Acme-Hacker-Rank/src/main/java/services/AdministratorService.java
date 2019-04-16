
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.TickerGenerator;
import domain.Actor;
import domain.Administrator;
import domain.Box;
import domain.Customisation;
import domain.SocialProfile;
import forms.AdministratorForm;

@Service
@Transactional
public class AdministratorService {

	@Autowired
	public AdministratorRepository	adminRepository;

	@Autowired
	public ActorService				actorService;

	@Autowired
	public CustomisationService		customisationService;


	//Constructor
	public AdministratorService() {
		super();
	}

	public Administrator create() {

		Administrator result;
		result = new Administrator();

		final Collection<Box> predefined = new ArrayList<Box>();

		final UserAccount newUser = new UserAccount();
		final Authority f = new Authority();
		f.setAuthority(Authority.ADMIN);
		newUser.addAuthority(f);
		result.setUserAccount(newUser);

		result.setSocialProfiles(new ArrayList<SocialProfile>());
		result.setName("");
		result.setEmail("");
		result.setAddress("");
		result.setSurname("");
		result.setPhoneNumber("");
		result.setPhoto("");

		// Administrator

		return result;
	}

	public Administrator findByPrincipal() {
		Administrator res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.findByUserAccount(userAccount);
		Assert.notNull(res);

		return res;
	}

	public Administrator findOnePrincipal() {
		final Actor a = this.actorService.findByPrincipal();
		return this.adminRepository.findOne(a.getId());
	}

	public Administrator findOne(final Administrator Administrator) {
		return this.adminRepository.findOne(Administrator.getId());
	}

	public Collection<Administrator> findAll() {
		return this.adminRepository.findAll();
	}

	public Administrator save(final Administrator Administrator) {
		Assert.notNull(Administrator);
		Assert.isTrue(!Administrator.getIsBanned());

		final String pnumber = Administrator.getPhoneNumber();
		final Customisation cus = ((List<Customisation>) this.customisationService.findAll()).get(0);
		final String cc = cus.getPhoneNumberCode();
		if (pnumber.matches("^[0-9]{4,}$"))
			Administrator.setPhoneNumber(cc.concat(pnumber));

		if (Administrator.getId() != 0) {
			Assert.isTrue(this.actorService.checkAdmin());

			// Modified Administrator must be logged Administrator
			final Administrator logAdministrator;
			logAdministrator = this.findByPrincipal();
			Assert.notNull(logAdministrator);
			Assert.notNull(logAdministrator.getId());

		} else {

			final Collection<Box> boxes = this.actorService.createPredefinedBoxes();
			Administrator.setBoxes(boxes);
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String oldpass = Administrator.getUserAccount().getPassword();
			final String hash = encoder.encodePassword(oldpass, null);

			final UserAccount cuenta = Administrator.getUserAccount();
			cuenta.setPassword(hash);
			Administrator.setUserAccount(cuenta);
		}
		// Restrictions
		Administrator res;

		res = this.adminRepository.save(Administrator);
		return res;
	}

	public Administrator findByUserAccount(final UserAccount userAccount) {
		Administrator res;
		Assert.notNull(userAccount);

		res = this.adminRepository.findByUserAccountId(userAccount.getId());

		return res;
	}

	public Administrator saveForTest(final Administrator bro) {

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

		return this.adminRepository.save(bro);
	}
	public Administrator findOne(final int AdministratorId) {
		Administrator c;

		Assert.notNull(AdministratorId);
		Assert.isTrue(AdministratorId != 0);
		c = this.adminRepository.findOne(AdministratorId);

		Assert.notNull(c);
		return c;
	}

	public Administrator reconstruct(final AdministratorForm adminForm, final BindingResult binding) {
		final Administrator admin = this.create();

		//Assert.isTrue(AdministratorForm.getUserAccount().getAuthorities() == colMem);
		//Damos valores a los atributos de la hermandad con los datos que nos llegan
		final Authority com = new Authority();
		com.setAuthority(Authority.ADMIN);
		final List<Authority> aus = new ArrayList<>();
		aus.add(com);
		final UserAccount ua = adminForm.getUserAccount();
		ua.setAuthorities(aus);

		admin.setAddress(adminForm.getAddress());
		admin.setEmail(adminForm.getEmail());
		admin.setName(adminForm.getName());
		admin.setPhoneNumber(adminForm.getPhoneNumber());
		admin.setPhoto(adminForm.getPhoto());
		admin.setSurname(adminForm.getSurname());
		admin.setUserAccount(ua);
		admin.setVat(adminForm.getVat());

		admin.setHolderName(adminForm.getHolderName());
		admin.setMakeName(adminForm.getMakeName());
		admin.setNumber(adminForm.getNumber());
		admin.setExpirationMonth(adminForm.getExpirationMonth());
		admin.setExpirationYear(adminForm.getExpirationYear());
		admin.setCvv(adminForm.getCvv());

		admin.setIsBanned(false);

		this.validator.validate(admin, binding);
		if (binding.hasErrors())
			throw new ValidationException();

		return admin;
	}


	@Autowired
	private Validator	validator;


	public Administrator reconstruct(final Administrator admin, final BindingResult binding) {
		Administrator res;

		//Check authority
		final Authority a = new Authority();
		final Actor act = this.actorService.findByPrincipal();
		final UserAccount user = act.getUserAccount();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a) && user.getAuthorities().size() == 1);

		if (admin.getId() == 0)
			res = admin;
		else
			res = this.adminRepository.findOne(admin.getId());
		res.setName(admin.getName());
		res.setEmail(admin.getEmail());
		res.setSurname(admin.getSurname());
		res.setAddress(admin.getAddress());
		res.setPhoneNumber(admin.getPhoneNumber());
		res.setPhoto(admin.getPhoto());

		res.setHolderName(admin.getHolderName());
		res.setMakeName(admin.getMakeName());
		res.setNumber(admin.getNumber());
		res.setExpirationMonth(admin.getExpirationMonth());
		res.setExpirationYear(admin.getExpirationYear());
		res.setCvv(admin.getCvv());

		this.validator.validate(res, binding);
		if (binding.hasErrors())
			throw new ValidationException();

		return res;
	}

	public void leave() {
		final Administrator logAdministrator = this.findByPrincipal();

		logAdministrator.setAddress("Unknown");
		logAdministrator.setIsBanned(true);
		logAdministrator.setEmail("unknown@unknown.com");
		logAdministrator.setName("Unknown");
		logAdministrator.setPhoneNumber("Unknown");
		logAdministrator.setPhoto("http://www.unknown.com");

		logAdministrator.setSocialProfiles(null);
		logAdministrator.setSurname("Unknown");

		logAdministrator.setHolderName("Unknown");
		logAdministrator.setMakeName("Unknown");
		logAdministrator.setCvv(123);
		logAdministrator.setExpirationMonth(1);
		logAdministrator.setExpirationYear(9999);
		logAdministrator.setNumber("4532134223318979");

		final UserAccount ua = logAdministrator.getUserAccount();
		final String tick1 = TickerGenerator.tickerLeave();
		ua.setUsername("Unknown" + tick1);
		final String pass1 = TickerGenerator.generateTicker();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String pass2 = encoder.encodePassword(pass1, null);
		ua.setPassword(pass2);
		logAdministrator.setUserAccount(ua);
	}

	public void flush() {
		this.adminRepository.flush();
	}

}
