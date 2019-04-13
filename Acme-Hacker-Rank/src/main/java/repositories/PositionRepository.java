
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

	@Query("select p from Position p where p.company.id=?1")
	List<Position> findAllByCompanyId(int id);

	@Query("select p from Position p join p.problems p1 where p1.id=?1")
	List<Position> findByProblemId(int id);
}
