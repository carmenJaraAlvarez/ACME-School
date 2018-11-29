
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.EventRepository;

@Service
@Transactional
public class EventService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private EventRepository	eventRepository;


	// Constructors -----------------------------------------------------------

	public EventService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	// Other business methods -------------------------------------------------

}
