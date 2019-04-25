
package services;

import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProblemRepository;
import security.Authority;
import domain.Company;
import domain.Position;
import domain.Problem;

@Service
@Transactional
public class ProblemService {

	@Autowired
	private Validator			validator;

	@Autowired
	private ProblemRepository	problemRepository;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private CompanyService		companyService;


	public List<Problem> getAllProblemsByCompanyId(final int id) {
		this.checkCompany(id);
		return this.problemRepository.findAllByCompanyId(id);
	}

	public List<Problem> getAllProblemsByCompanyIdFinalMode(final int id) {
		this.checkCompany(id);
		return this.problemRepository.findAllByCompanyIdFinalMode(id);
	}
	void checkCompany(final int id) {
		Assert.isTrue(id == this.companyService.findOnePrincipal().getId());

	}

	private void checkConditions() {
		final Company c = this.companyService.findOnePrincipal();
		final Authority a = new Authority();
		a.setAuthority(Authority.COMPANY);
		Assert.isTrue(c.getUserAccount().getAuthorities().contains(a));
		Assert.isTrue(!c.getIsBanned());
	}

	public Problem findOne(final int id) {
		final Company c = this.companyService.findOnePrincipal();
		this.checkConditions();
		final Problem p = this.problemRepository.findOne(id);
		Assert.isTrue(this.getAllProblemsByCompanyId(c.getId()).contains(p));
		return p;
	}
	public Problem create() {
		final Company c = this.companyService.findOnePrincipal();
		final Problem res = new Problem();
		res.setId(0);
		res.setCompany(c);
		res.setFinalMode(false);
		return res;

	}

	public void save(final Problem p) {
		this.checkConditions();
		Assert.isTrue(p.getCompany().getId() == this.companyService.findOnePrincipal().getId());

		this.problemRepository.save(p);
	}

	public void deleteProblem(final Problem p) {
		final Company c = this.companyService.findOnePrincipal();
		Assert.isTrue(!p.isFinalMode());
		this.checkConditions();
		Assert.isTrue(this.getAllProblemsByCompanyId(c.getId()).contains(p));
		final List<Position> p1 = this.positionService.getPositionByProblemId(p.getId());
		for (final Position posi : p1) {
			posi.getProblems().remove(p);
			this.positionService.save(posi);
		}
		this.problemRepository.delete(p.getId());
	}

	public Problem reconstruct(final Problem p, final BindingResult binding) {
		Problem res;
		if (p.getId() == 0)
			res = p;
		else {
			res = this.findOne(p.getId());
			Assert.isTrue(res.isFinalMode() != true);
			res.setAttachments(p.getAttachments());
			res.setFinalMode(p.isFinalMode());
			res.setHint(p.getHint());
			res.setStatement(p.getStatement());
			res.setTitle(p.getTitle());

		}
		this.validator.validate(res, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return res;
	}

	public void flush() {
		this.problemRepository.flush();

	}

	public List<Problem> getProblems(final Position position) {
		final List<Problem> res = this.problemRepository.findAllByCompanyId(this.positionService.getThisCompany().getId());

		return res;
	}

}
