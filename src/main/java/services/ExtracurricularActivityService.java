
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ExtracurricularActivityRepository;

@Service
@Transactional
public class ExtracurricularActivityService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ExtracurricularActivityRepository	extracurricularActivityRepository;


	// Constructors -----------------------------------------------------------

	public ExtracurricularActivityService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	// Other business methods -------------------------------------------------

}
