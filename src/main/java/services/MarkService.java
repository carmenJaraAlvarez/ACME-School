
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.MarkRepository;

@Service
@Transactional
public class MarkService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MarkRepository	markRepository;


	// Constructors -----------------------------------------------------------

	public MarkService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	// Other business methods -------------------------------------------------

}
