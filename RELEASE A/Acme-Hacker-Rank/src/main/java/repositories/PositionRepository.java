
package repositories;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

	@Query("select p from Position p where p.company.id=?1")
	List<Position> findAllByCompanyId(int id);
	@Query("select p from Position p join p.skills s join p.technologies t where (p.title like %?1% or p.description like %?1% or p.profile like %?1% or s like %?1% or t like %?1% or p.company.companyName like %?1%) and p.finalMode=1 and p.isCancelled=0 group by p.id")
	Collection<Position> searchPositions(String keyword);

	@Query("select p from Position p join p.skills s join p.technologies t where (p.title like %?1% or p.description like %?1% or t like %?1% or s like %?1% or p.ticker like %?1% or p.profile like %?1%) and p.finalMode=1 and p.isCancelled=0 group by p.id")
	Collection<Position> finderKeyword(String keyword);

	@Query("select p from Position p where p.deadline = ?1 and p.finalMode=1 and p.isCancelled=0")
	Collection<Position> finderDeadline(Date deadline);

	@Query("select p from Position p where p.deadline <= ?1 and p.finalMode=1 and p.isCancelled=0")
	Collection<Position> finderMaxDeadline(Date deadline);

	@Query("select p from Position p where p.salary >= ?1 and p.finalMode=1 and p.isCancelled=0")
	Collection<Position> finderSalary(Integer salary);

	@Query("select p from Position p join p.problems p1 where p1.id=?1")
	List<Position> findByProblemId(int id);

	@Query("select p from Position p where p.finalMode = 1")
	Collection<Position> findPositionFinalMode();

	@Query("select p from Position p where p.finalMode = 1 and p.isCancelled = 0")
	Collection<Position> findPositionFinalModeNotCancelled();

}
