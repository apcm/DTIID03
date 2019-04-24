
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Problem;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Integer> {

	@Query("select p from Problem p where p.company.id=?1")
	List<Problem> findAllByCompanyId(int id);

	@Query("select p from Problem p where p.finalMode=1 and p.company.id=?1")
	List<Problem> findAllByCompanyIdFinalMode(int id);

}
