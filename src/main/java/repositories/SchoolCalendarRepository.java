
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.SchoolCalendar;

@Repository
public interface SchoolCalendarRepository extends JpaRepository<SchoolCalendar, Integer> {

}
