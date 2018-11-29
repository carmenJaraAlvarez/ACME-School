
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ParentsGroup;

@Repository
public interface ParentsGroupRepository extends JpaRepository<ParentsGroup, Integer> {

	@Query("select pg from ParentsGroup pg where pg.name LIKE concat('%',?1,'%') or pg.description LIKE concat('%',?1,'%')")
	Collection<ParentsGroup> findByKeyWord(String keyWord);

}
