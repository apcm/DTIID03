
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

	@Query("select p.title from Position p join p.skills s join p.technologies t where p.title like %?1% or p.description like %?1% or p.profile like %?1% or s like %?1% or t like %?1% or p.company.companyName like %?1% group by p.id")
	Collection<String> searchPositions(String keyword);
}
