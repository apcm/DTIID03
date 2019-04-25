
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;
import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	@Query("select a1 from Actor a1 where a1.userAccount = ?1")
	public Actor getActor(UserAccount ua);

	@Query("select a from Actor a where a.userAccount.id = ?1")
	Actor findByUserAccountId(int userAccount);

	@Query("select a from Actor a where a.isBanned = 1")
	List<Actor> findAllBanned();

	@Query("select a from Actor a where a.isBanned = 0")
	List<Actor> findAllNotBanned();

	@Query("select count(m1) *1.0/(select count(m2) from Actor a join a.boxes b join b.messages m2 where m2.sender.id=?1) from Actor a join a.boxes b join b.messages m1 where m1.flagSpam = 1 and m1.sender.id=?1")
	public Double flagSpamMessagesCount(int id);

}
