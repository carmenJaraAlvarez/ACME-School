
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.StudentMessageRepository;

@Service
@Transactional
public class StudentMessageService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private StudentMessageRepository	studentMessageRepository;


	// Constructors -----------------------------------------------------------

	public StudentMessageService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	// Other business methods -------------------------------------------------

}
