
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Mark;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Integer> {

	@Query("select m from Mark m where m.student.id=?1")
	Collection<Mark> findByStudent(int studentId);

	@Query("select count(m) from Mark m where m.value >= 5 and datediff(m.date, CURRENT_DATE) <= 365)")
	int getPassedMarks();
}
