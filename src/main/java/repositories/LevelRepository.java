
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Level;

@Repository
public interface LevelRepository extends JpaRepository<Level, Integer> {

}
