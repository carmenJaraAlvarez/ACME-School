
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

	//Para encontrar un student a traves de su userAccount
	@Query("select a from Student a where a.userAccount.id=?1")
	Student findByUserAccount(int userAccountId);

}
