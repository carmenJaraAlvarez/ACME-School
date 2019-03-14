
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Homework;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, Integer> {

	@Query("select coalesce(min(m.homeworks.size),0.0) from Subject m")
	Double minHomeworkPerSubject();

	@Query("select coalesce(max(m.homeworks.size),0.0) from Subject m")
	Double maxHomeworkPerSubject();

	@Query("select coalesce(avg(n.homeworks.size),0.0) from Subject n")
	Double averageHomeworkPerSubject();

	@Query("select coalesce(sqrt(sum(n.homeworks.size * n.homeworks.size) / count(n.homeworks.size) - (avg(n.homeworks.size) * avg(n.homeworks.size))),0.0) from Subject n")
	Double standardDeviationHomeworkPerSubject();
}
