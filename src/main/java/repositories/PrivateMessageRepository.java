
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.PrivateMessage;

@Repository
public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, Integer> {

	@Query("select e from PrivateMessage e where e.actorSender.id = ?1")
	PrivateMessage findByUserAccountIdSender(int id);

}
