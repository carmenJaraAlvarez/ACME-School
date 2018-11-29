
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.SubjectRepository;

@Service
@Transactional
public class SubjectService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SubjectRepository	subjectRepository;


	// Constructors -----------------------------------------------------------

	public SubjectService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	// Other business methods -------------------------------------------------

}
