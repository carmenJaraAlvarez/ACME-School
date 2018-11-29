
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.EntryRepository;

@Service
@Transactional
public class EntryService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private EntryRepository	entryRepository;


	// Constructors -----------------------------------------------------------

	public EntryService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	// Other business methods -------------------------------------------------

}
