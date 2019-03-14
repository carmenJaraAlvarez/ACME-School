
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.StudentMessage;

@Repository
public interface StudentMessageRepository extends JpaRepository<StudentMessage, Integer> {

	@Query("select coalesce(avg(s.students.size),0.0) from ClassGroup s")
	Double averageStudentsPerClassGroup();

	@Query("select coalesce(sqrt(sum(n.students.size * n.students.size) / count(n.students.size) - (avg(n.students.size) * avg(n.students.size))),0.0) from ClassGroup n")
	Double standardDeviationStudentsPerClassGroup();
}
