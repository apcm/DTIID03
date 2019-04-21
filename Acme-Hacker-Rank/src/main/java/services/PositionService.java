package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Company;
import domain.Position;
import domain.Problem;

import repositories.CompanyRepository;
import repositories.PositionRepository;
import security.LoginService;
import security.UserAccount;
import utilities.TickerGenerator;

@Service
@Transactional
public class PositionService {
	
	@Autowired
	private PositionRepository positionRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private ProblemService problemService;
	
	public Position create(){
		Position res = new Position();
		res.setFinalMode(false);
		res.setIsCancelled(false);
		String ticker = TickerGenerator.tickerPosition();
		res.setTicker(ticker);
		Collection<Problem> problems = new ArrayList<Problem>();
		res.setProblems(problems);
		
		return res;
	}
	
	public Collection<Position> findAll(){
		return positionRepository.findAll();
	}
	
	public Position findOne(int positionId){
		return positionRepository.findOne(positionId);
	}
	
	public Position save(Position position){
		Position res = new Position();		
		if(has2Problem(position)){
		res = positionRepository.save(position);
		res.setCompany(this.getThisCompany());
		}
		
		
		return res;
	}
	
	private boolean has2Problem(Position position) {
		Boolean res = true;
		if(position.isFinalMode()){
			if(position.getProblems().size()< 2){
				res = false;
			}
		}
			
		return res;
	}


	public void delete(Position position){
		positionRepository.delete(position);
	}

	public Collection<Position> getMyPositionList() {
		Collection<Position> positions = this.positionsByCompany(this.findAll());
		return positions;
	}

	private Collection<Position> positionsByCompany(Collection<Position> all) {
		Company actual = this.getThisCompany();
		Collection<Position> positions = new ArrayList<Position>();
		for(Position p : all){
			if(p.getCompany().equals(actual)){
				positions.add(p);
			}
		}
		return positions;
	}
	
	private Company getThisCompany(){
		Company res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.companyRepository.findByUserAccountId(userAccount.getId());
		return res;
	}

	
	public Position cancelP(int positionId) {
		
		Position p = this.findOne(positionId);
		p.setIsCancelled(true);
		Position res = this.save(p);
		
		return res;
	}

	public Collection<Problem> getProblems(Position position) {
		Collection<Problem> res = new ArrayList<Problem>();
		Collection<Problem> all = this.problemService.findAll();
		for ( Problem p: all){
			if(p.getCompany().equals(getThisCompany())){
				res.add(p);
			}
		}
		
		return res;
	}

}

