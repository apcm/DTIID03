
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ProblemRepository;
import domain.Problem;

@Service
@Transactional
public class ProblemService {
	
	@Autowired
	private ProblemRepository problemRepository;
	
	public Collection<Problem> findAll(){
		return problemRepository.findAll();
	}

}
