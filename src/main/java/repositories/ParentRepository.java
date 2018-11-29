
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Parent;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Integer> {

	//Para encontrar un parent a traves de su userAccount
	@Query("select a from Parent a where a.userAccount.id=?1")
	Parent findByUserAccount(int userAccountId);

}
