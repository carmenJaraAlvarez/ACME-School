
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.SchoolCalendarRepository;

@Service
@Transactional
public class SchoolCalendarService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SchoolCalendarRepository	schoolCalendarRepository;


	// Constructors -----------------------------------------------------------

	public SchoolCalendarService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	// Other business methods -------------------------------------------------

}
