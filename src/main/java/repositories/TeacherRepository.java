
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

}
