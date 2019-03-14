
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

	//Para encontrar un teacher a traves de su userAccount
	@Query("select a from Teacher a where a.userAccount.id=?1")
	Teacher findByUserAccount(int userAccountId);

	@Query("select coalesce(avg(s.teachers.size),0.0) from School s")
	Double averageTeachersPerSchool();

	@Query("select coalesce(sqrt(sum(n.teachers.size * n.teachers.size) / count(n.teachers.size) - (avg(n.teachers.size) * avg(n.teachers.size))),0.0) from School n")
	Double standardDeviationTeachersPerSchool();

	//Para encontrar un profesor a traves de su userName
	@Query("select a from Teacher a where a.userAccount.username=?1")
	Teacher findByUserName(String userName);
}
