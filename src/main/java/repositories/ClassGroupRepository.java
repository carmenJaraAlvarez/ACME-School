
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.ClassGroup;

@Repository
public interface ClassGroupRepository extends JpaRepository<ClassGroup, Integer> {

}
