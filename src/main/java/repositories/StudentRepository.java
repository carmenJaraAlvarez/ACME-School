
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

	//Para encontrar un student a traves de su userAccount
	@Query("select a from Student a where a.userAccount.id=?1")
	Student findByUserAccount(int userAccountId);

	@Query("select a from Student a where a.classGroup.id=?1")
	Collection<Student> findByClass(int classGroupId);

	@Query("select a from Student a where a.chatRooms.size=0")
	Collection<Student> findIsolated();

	@Query("select coalesce(avg(s.students.size),0.0) from ClassGroup s")
	Double averageStudentsPerClass();

	@Query("select coalesce(sqrt(sum(n.students.size * n.students.size) / count(n.students.size) - (avg(n.students.size) * avg(n.students.size))),0.0) from ClassGroup n")
	Double standardDeviationStudentsPerClass();
}
