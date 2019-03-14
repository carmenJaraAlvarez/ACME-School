
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Parent;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Integer> {

	//Para encontrar un parent a traves de su userAccount
	@Query("select a from Parent a where a.userAccount.id=?1")
	Parent findByUserAccount(int userAccountId);

	@Query("select coalesce(min(m.members.size+1+m.groupAdmins.size),0.0) from ParentsGroup m")
	Double minTutorsPerGroup();

	@Query("select coalesce(max(m.members.size+1+m.groupAdmins.size),0.0) from ParentsGroup m")
	Double maxTutorsPerGroup();

	@Query("select coalesce(avg(n.members.size+1+n.groupAdmins.size),0.0) from ParentsGroup n")
	Double averageTutorsPerGroup();

	@Query("select coalesce(sqrt(sum(n.members.size * n.members.size) / count(n.members.size) - (avg(n.members.size) * avg(n.members.size))),0.0) from ParentsGroup n")
	Double standardDeviationTutorsPerGroup();

	@Query("select distinct p from Parent p, Message m where datediff(m.moment, CURRENT_DATE) <= 7) and m.parent.id = p.id")
	Collection<Parent> getParentsWithMessagesLastWeek();

	@Query("select distinct p from Parent p, Message m where datediff(m.moment, CURRENT_DATE) <= 14) and m.parent.id = p.id")
	Collection<Parent> getParentsWithMessagesLast2Week();
}
