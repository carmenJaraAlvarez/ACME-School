
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.HomeworkRepository;

@Service
@Transactional
public class HomeworkService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private HomeworkRepository	homeworkRepository;


	// Constructors -----------------------------------------------------------

	public HomeworkService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	// Other business methods -------------------------------------------------

}
