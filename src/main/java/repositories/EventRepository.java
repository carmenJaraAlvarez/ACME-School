
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

	@Query("select coalesce(min(m.events.size),0.0) from ParentsGroup m")
	Double minEventsPerGroup();

	@Query("select coalesce(max(m.events.size),0.0) from ParentsGroup m")
	Double maxEventsPerGroup();

	@Query("select coalesce(avg(n.events.size),0.0) from ParentsGroup n")
	Double averageEventsPerGroup();

	@Query("select coalesce(sqrt(sum(n.events.size * n.events.size) / count(n.events.size) - (avg(n.events.size) * avg(n.events.size))),0.0) from ParentsGroup n")
	Double standardDeviationEventsPerGroup();

}
