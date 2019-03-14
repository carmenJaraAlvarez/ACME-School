
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Message;
import domain.ParentsGroup;

@Repository
public interface ParentsGroupRepository extends JpaRepository<ParentsGroup, Integer> {

	@Query("select pg from ParentsGroup pg where pg.name LIKE concat('%',?1,'%') or pg.description LIKE concat('%',?1,'%')")
	Collection<ParentsGroup> findByKeyWord(String keyWord);

	@Query("select m from ParentsGroup pg join pg.messages m where pg.id=?1")
	Collection<Message> findGroupMessages(int parentsGroupId);

	//Se devolverán todos aquellos grupos que tengan la cantidad maxima de miembros
	@Query("select e from ParentsGroup e where e.members.size = (Select MAX(p.members.size) from ParentsGroup p)")
	Collection<ParentsGroup> getGroupWithMostTutors();
}
