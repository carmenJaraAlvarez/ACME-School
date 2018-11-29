
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Entry;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Integer> {

}
