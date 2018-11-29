
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Homework;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, Integer> {

}
