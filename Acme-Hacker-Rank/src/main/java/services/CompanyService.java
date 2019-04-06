
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

import repositories.CompanyRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.TickerGenerator;
import domain.Actor;
import domain.Box;
import domain.Company;
import domain.Customisation;
import domain.SocialProfile;
import forms.CompanyForm;

@Service
@Transactional
public class CompanyService {

	@Autowired
	public CompanyRepository	companyRepository;

	@Autowired
	public ActorService			actorService;

	@Autowired
	public CustomisationService	customisationService;


	//Constructor
	public CompanyService() {
		super();
	}

	public Company create() {

		Company result;
		result = new Company();

		final Collection<Box> predefined = new ArrayList<Box>();

		final UserAccount newUser = new UserAccount();
		final Authority f = new Authority();
		f.setAuthority(Authority.COMPANY);
		newUser.addAuthority(f);
		result.setUserAccount(newUser);

		result.setSocialProfiles(new ArrayList<SocialProfile>());
		result.setName("");
		result.setEmail("");
		result.setAddress("");
		result.setSurname("");
		result.setPhoneNumber("");
		result.setPhoto("");

		// Company
		result.setCompanyName("");

		return result;
	}

	public Company findByPrincipal() {
		Company res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.findByUserAccount(userAccount);
		Assert.notNull(res);

		return res;
	}

	public Company findOnePrincipal() {
		final Actor a = this.actorService.findByPrincipal();
		return this.companyRepository.findOne(a.getId());
	}

	public Company findOne(final Company Company) {
		return this.companyRepository.findOne(Company.getId());
	}

	public Collection<Company> findAll() {
		return this.companyRepository.findAll();
	}

	public Company save(final Company Company) {
		Assert.notNull(Company);
		Assert.isTrue(!Company.getIsBanned());

		final String pnumber = Company.getPhoneNumber();
		final Customisation cus = ((List<Customisation>) this.customisationService.findAll()).get(0);
		final String cc = cus.getPhoneNumberCode();
		if (pnumber.matches("^[0-9]{4,}$"))
			Company.setPhoneNumber(cc.concat(pnumber));

		if (Company.getId() != 0) {
			Assert.isTrue(this.actorService.checkCompany());

			// Modified Company must be logged Company
			final Company logCompany;
			logCompany = this.findByPrincipal();
			Assert.notNull(logCompany);
			Assert.notNull(logCompany.getId());

		} else {

			final Collection<Box> boxes = this.actorService.createPredefinedBoxes();
			Company.setBoxes(boxes);
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String oldpass = Company.getUserAccount().getPassword();
			final String hash = encoder.encodePassword(oldpass, null);

			final UserAccount cuenta = Company.getUserAccount();
			cuenta.setPassword(hash);
			Company.setUserAccount(cuenta);
		}
		// Restrictions
		Company res;

		res = this.companyRepository.save(Company);
		return res;
	}

	public Company findByUserAccount(final UserAccount userAccount) {
		Company res;
		Assert.notNull(userAccount);

		res = this.companyRepository.findByUserAccountId(userAccount.getId());

		return res;
	}

	public Company saveForTest(final Company bro) {

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

		return this.companyRepository.save(bro);
	}
	public Company findOne(final int CompanyId) {
		Company c;

		Assert.notNull(CompanyId);
		Assert.isTrue(CompanyId != 0);
		c = this.companyRepository.findOne(CompanyId);

		Assert.notNull(c);
		return c;
	}

	public Company reconstruct(final CompanyForm companyForm, final BindingResult binding) {
		final Company company = this.create();

		//Assert.isTrue(CompanyForm.isConditionsAccepted());
		final Authority bro = new Authority();
		bro.setAuthority(Authority.COMPANY);
		Assert.isTrue(companyForm.getUserAccount().getAuthorities().contains(bro));
		final Collection<Authority> colMem = new ArrayList<Authority>();
		colMem.add(bro);
		//Assert.isTrue(CompanyForm.getUserAccount().getAuthorities() == colMem);
		//Damos valores a los atributos de la hermandad con los datos que nos llegan
		company.setAddress(companyForm.getAddress());
		company.setEmail(companyForm.getEmail());
		company.setName(companyForm.getName());
		company.setPhoneNumber(companyForm.getPhoneNumber());
		company.setPhoto(companyForm.getPhoto());
		company.setSurname(companyForm.getSurname());
		company.setUserAccount(companyForm.getUserAccount());

		company.setName(companyForm.getName());

		company.setIsBanned(false);

		this.validator.validate(company, binding);
		if (binding.hasErrors())
			throw new ValidationException();

		return company;
	}


	@Autowired
	private Validator	validator;


	public Company reconstruct(final Company company, final BindingResult binding) {
		Company res;

		//Check authority
		final Authority a = new Authority();
		final Actor act = this.actorService.findByPrincipal();
		final UserAccount user = act.getUserAccount();
		a.setAuthority(Authority.COMPANY);
		Assert.isTrue(user.getAuthorities().contains(a) && user.getAuthorities().size() == 1);

		if (company.getId() == 0)
			res = company;
		else
			res = this.companyRepository.findOne(company.getId());
		res.setName(company.getName());
		res.setEmail(company.getEmail());
		res.setSurname(company.getSurname());
		res.setAddress(company.getAddress());
		res.setPhoneNumber(company.getPhoneNumber());
		res.setPhoto(company.getPhoto());
		this.validator.validate(res, binding);
		if (binding.hasErrors())
			throw new ValidationException();

		return res;
	}

	private void checkAuthority() {
		UserAccount ua;
		ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		final Collection<Authority> auth = ua.getAuthorities();
		final Authority a = new Authority();
		a.setAuthority(Authority.COMPANY);
		Assert.isTrue(auth.contains(a));
	}

	public void leave() {
		final Company logCompany = this.findByPrincipal();

		logCompany.setAddress("Unknown");
		logCompany.setIsBanned(true);
		logCompany.setEmail("unknown@unknown.com");
		logCompany.setName("Unknown");
		logCompany.setPhoneNumber("Unknown");
		logCompany.setPhoto("http://www.unknown.com");

		logCompany.setSocialProfiles(null);
		logCompany.setSurname("Unknown");

		logCompany.setCompanyName("Unknown");

		final UserAccount ua = logCompany.getUserAccount();
		final String tick1 = TickerGenerator.tickerLeave();
		ua.setUsername("Unknown" + tick1);
		final String pass1 = TickerGenerator.generateTicker();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String pass2 = encoder.encodePassword(pass1, null);
		ua.setPassword(pass2);
		logCompany.setUserAccount(ua);
	}

}