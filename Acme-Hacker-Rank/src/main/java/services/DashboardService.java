
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.DashboardRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Company;
import domain.Hacker;
import domain.Position;

@Service
@Transactional
public class DashboardService {

	@Autowired
	DashboardRepository	repository;


	private boolean checkAdmin() {
		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		final UserAccount user = LoginService.getPrincipal();
		return user.getAuthorities().contains(a);
	}

	public Double avgPositions() {
		Assert.isTrue(this.checkAdmin());
		return this.repository.avgPositions();
	}

	public Integer minPositions() {
		Assert.isTrue(this.checkAdmin());
		return this.repository.minPositions();
	}

	public Integer maxPositions() {
		Assert.isTrue(this.checkAdmin());
		return this.repository.maxPositions();
	}

	public Double stddevPositions() {
		Assert.isTrue(this.checkAdmin());
		return this.repository.stddevPositions();
	}

	public Double avgApplications() {
		Assert.isTrue(this.checkAdmin());
		return this.repository.avgApplications();
	}

	public Integer minApplications() {
		Assert.isTrue(this.checkAdmin());
		return this.repository.minApplications();
	}

	public Integer maxApplications() {
		Assert.isTrue(this.checkAdmin());
		return this.repository.maxApplications();
	}

	public Double stddevApplications() {
		Assert.isTrue(this.checkAdmin());
		return this.repository.stddevApplications();
	}

	public Collection<Company> companiesWithMorePositions() {
		Assert.isTrue(this.checkAdmin());
		return this.repository.companiesWithMorePositions();
	}

	public Collection<Hacker> hackersWithMorePositions() {
		Assert.isTrue(this.checkAdmin());
		return this.repository.hackersWithMoreApplications();
	}

	public Double avgSalary() {
		Assert.isTrue(this.checkAdmin());
		return this.repository.avgSalary();
	}

	public Integer minSalary() {
		Assert.isTrue(this.checkAdmin());
		return this.repository.minSalary();
	}

	public Integer maxSalary() {
		Assert.isTrue(this.checkAdmin());
		return this.repository.maxSalary();
	}

	public Double stddevSalary() {
		Assert.isTrue(this.checkAdmin());
		return this.repository.stddevSalary();
	}

	public Collection<Position> highestSalaryPosition() {
		Assert.isTrue(this.checkAdmin());
		return this.repository.highestSalaryPosition();
	}

	public Collection<Position> lowestSalaryPosition() {
		Assert.isTrue(this.checkAdmin());
		return this.repository.lowestSalaryPosition();
	}

	public Integer minCurricula() {
		Assert.isTrue(this.checkAdmin());
		return this.repository.minCurricula();
	}

	public Integer maxCurricula() {
		Assert.isTrue(this.checkAdmin());
		return this.repository.maxCurricula();
	}

	public Double avgCurricula() {
		Assert.isTrue(this.checkAdmin());
		return this.repository.avgCurricula();
	}

	public Double stddevCurricula() {
		Assert.isTrue(this.checkAdmin());
		return this.repository.stddevCurricula();
	}

	public Integer minResults() {
		Assert.isTrue(this.checkAdmin());
		return this.repository.minResults();
	}

	public Integer maxResults() {
		Assert.isTrue(this.checkAdmin());
		return this.repository.maxResults();
	}

	public Double avgResults() {
		Assert.isTrue(this.checkAdmin());
		return this.repository.avgResults();
	}

	public Double stddevResults() {
		Assert.isTrue(this.checkAdmin());
		return this.repository.stddevResults();
	}

	public Double ratioFinders() {
		Assert.isTrue(this.checkAdmin());
		return this.repository.ratioFinders();
	}

}
