
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

	//Se devolverán todos aquellos grupos que tengan la cantidad maxima de estudiantes
	@Query("select e from School e where e.teachers.size = (Select MAX(p.teachers.size) from School p)")
	Collection<School> getSchoolWithMostTeachers();
}
