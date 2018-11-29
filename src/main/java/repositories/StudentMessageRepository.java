
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.StudentMessage;

@Repository
public interface StudentMessageRepository extends JpaRepository<StudentMessage, Integer> {

}
