
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;
import domain.Hacker;
import domain.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

	@Query("select a from Application a where ?1 = a.hacker")
	List<Application> getApplicationsByHacker(Hacker p);

}
