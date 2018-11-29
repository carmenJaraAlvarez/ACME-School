
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.School;

@Repository
public interface SchoolRepository extends JpaRepository<School, Integer> {

	@Query("select s from School s where s.nameSchool LIKE concat('%',?1,'%')")
	Collection<School> findByKeyWord(String keyWord);

}
