
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ClassTime;

@Repository
public interface ClassTimeRepository extends JpaRepository<ClassTime, Integer> {

	@Query("select c from ClassTime c where c.classGroup.id=?1")
	Collection<ClassTime> findByClass(int classId);

	@Query("select c from ClassTime c where c.classGroup.id=?1 and c.day='1' order by c.hour")
	Collection<ClassTime> findMon(int classGroupId);
	@Query("select c from ClassTime c where c.classGroup.id=?1 and c.day='2' order by c.hour")
	Collection<ClassTime> findTue(int classGroupId);
	@Query("select c from ClassTime c where c.classGroup.id=?1 and c.day='3' order by c.hour")
	Collection<ClassTime> findWed(int classGroupId);
	@Query("select c from ClassTime c where c.classGroup.id=?1 and c.day='4' order by c.hour")
	Collection<ClassTime> findThu(int classGroupId);
	@Query("select c from ClassTime c where c.classGroup.id=?1 and c.day='5' order by c.hour")
	Collection<ClassTime> findFri(int classGroupId);
	@Query("select c from ClassTime c where c.classGroup.id=?1 and c.day LIKE ?2 and c.subject.name LIKE ?3")
	Collection<ClassTime> findBySubject(int classGroupId, String day, String subject);
}
