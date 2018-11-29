
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.PrivateMessageRepository;

@Service
@Transactional
public class PrivateMessageService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PrivateMessageRepository	privateMessageRepository;


	// Constructors -----------------------------------------------------------

	public PrivateMessageService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	// Other business methods -------------------------------------------------

}
