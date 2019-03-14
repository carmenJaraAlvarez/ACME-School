
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {

	@Query("select l.subjects from Level l where l.id=?1")
	Collection<Subject> finByLevel(int levelId);

}
